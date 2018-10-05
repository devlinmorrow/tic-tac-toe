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

(defn minimise 
  [board marker depth maximiser]
  (reduce (fn [current-best next-possible-move] 
            (if (and (some? (:alpha current-best)) 
                     (some? (:beta current-best))
                     (> (:alpha current-best) (:beta current-best)))
              current-best
              (let [current-score (:current-score current-best)
                    next-score (if (terminal-state? board) 
                                 (score-terminal-board board
                                                       depth
                                                       maximiser)
                                 (let [next-marker (get-opp-marker marker)] 
                                   (:current-score (maximise (place-mark board next-possible-move next-marker)
                                                             next-marker
                                                             (inc depth)
                                                             maximiser))))
                    updated-score (if (or (nil? current-score) 
                                          (> next-score current-score))
                                    next-score
                                    current-score)
                    current-beta (:beta current-best)
                    updated-beta (if (< updated-score current-beta)
                                    updated-score
                                    current-beta)]
                {:current-score updated-score
                 :alpha (:alpha current-best)
                 :beta updated-beta})))
          {:current-score nil
           :alpha nil
           :beta nil}
          (get-possible-moves board)))

(defn maximise
  [board marker depth maximiser]
  (reduce (fn [current-best next-possible-move] 
            (let [first-score (if (terminal-state? board) 
                                 (score-terminal-board board
                                                       depth
                                                       maximiser)
                                 (let [next-marker (get-opp-marker marker)] 
                                   (:current-score (minimise (place-mark board next-possible-move next-marker)
                                                             next-marker
                                                             (inc depth)
                                                             maximiser))))
                  (if (> first-score (:beta current-best))
                    first-score

                    current-alpha (:current-alpha current-best)
                    updated-alpha (if (> updated-score current-alpha)
                                    updated-score
                                    current-alpha)]
                {:current-score updated-score
                 :alpha updated-alpha
                 :beta (:beta current-best)})))
          {:current-score nil
           :alpha -1000
           :beta 1000}
          (get-possible-moves board)))

            (if (>= (:alpha current-best) (:beta current-best))
              current-best
              (let [current-score (:current-score current-best)
                    next-score (if (terminal-state? board) 
                                 (score-terminal-board board
                                                       depth
                                                       maximiser)
                                 (let [next-marker (get-opp-marker marker)] 
                                   (:current-score (minimise (place-mark board next-possible-move next-marker)
                                                             next-marker
                                                             (inc depth)
                                                             maximiser))))
                    updated-score (if (or (nil? current-score) 
                                          (> next-score current-score))
                                    next-score
                                    current-score)
                    current-alpha (:current-alpha current-best)
                    updated-alpha (if (> updated-score current-alpha)
                                    updated-score
                                    current-alpha)]
                {:current-score updated-score
                 :alpha updated-alpha
                 :beta (:beta current-best)})))
          {:current-score nil
           :alpha -1000
           :beta 1000}
          (get-possible-moves board)))

(defn get-tile-from-computer
  [board marker depth maximiser]
  (let [optimum-move (maximise board
                               marker
                               depth
                               maximiser)]
    (:best-move optimum-move)))
