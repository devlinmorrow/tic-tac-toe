(ns tic-tac-toe.unbeatable-comp (:require [tic-tac-toe.board :refer [get-possible-moves
                                                                     place-mark
                                                                     terminal-state?
                                                                     winner?]]
                                          [tic-tac-toe.marks :refer :all])) 

(def tie 0)
(def maximum-depth 10)

(defn get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(defn at-max-depth?
  [depth]
  (>= depth maximum-depth))

(defn score-terminal-board
  [board depth maximiser]
  (let [winner (winner? board)]
    (cond
      (= winner maximiser) (- maximum-depth depth)
      (nil? winner) tie
      :else (- depth maximum-depth))))

(declare maximise)
(declare minimise)

(defn maximise
  [board marker depth maximiser higher-alpha higher-beta]
  (if (terminal-state? board) 
    {:current-score (score-terminal-board board 
                                          depth 
                                          maximiser)}
    (reduce (fn [current-stuff next-possible-move]
              (let [current-alpha (:alpha current-stuff)
                    new-alpha (if (or (nil? current-alpha)
                                      (> higher-alpha current-alpha))
                                higher-alpha
                                current-alpha)]
                (if (and (some? new-alpha)
                         (some? new-beta)
                         (> new-alpha new-beta))
                  current-stuff
                  (let [current-score (:current-score current-stuff)
                        next-marker (get-opp-marker marker)
                        next-score (:current-score (minimise (place-mark board next-possible-move next-marker)
                                                             next-marker
                                                             (inc depth)
                                                             maximiser
                                                             new-alpha
                                                             new-beta))
                        updated-score (if (or (nil? current-score) 
                                              (> next-score current-score))
                                        next-score
                                        current-score)
                        updated-alpha (if (> updated-score new-alpha)
                                        updated-score
                                        new-alpha)]
                    {:current-score updated-score
                     :alpha updated-alpha
                     :beta new-beta}))))
            {:current-score nil}
            (get-possible-moves board))))

(defn get-tile-from-computer
  [board marker depth maximiser]
  (let [optimum-move (maximise board
                               marker
                               depth
                               maximiser)]
    (:best-move optimum-move)))
