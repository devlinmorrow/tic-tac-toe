(ns tic-tac-toe.unbeatable-comp (:require [tic-tac-toe.board :refer [get-possible-moves
                                                                     place-mark
                                                                     terminal-state?
                                                                     winner?]]
                                          [tic-tac-toe.marks :refer :all])) 

(def tied-score 0)
(def maximum-depth 10)

(defn get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(declare maximise)

(defn minimise
  [board marker depth higher-alpha higher-beta]
  (reduce (fn [current-stuff next-possible-move]
            (let [simulated-board (place-mark board next-possible-move marker)
                  current-alpha (:alpha current-stuff)
                  current-beta (:beta current-stuff)
                  next-stuff (if (and (some? current-alpha)
                                      (some? current-beta)
                                      (> current-alpha current-beta))
                               current-stuff
                               (let [next-possible-score (if (terminal-state? simulated-board)
                                                          (let [winner (winner? simulated-board)]
                                                            (cond
                                                            (nil? winner) tied-score
                                                            (= marker winner) (- depth maximum-depth)
                                                            :else (- maximum-depth depth)))
                                                          (:current-score (maximise simulated-board 
                                                                                    (get-opp-marker marker) 
                                                                                    (inc depth) 
                                                                                    current-alpha 
                                                                                    current-beta)))
                                     current-score (:current-score current-stuff)
                                     next-score-better? (or (nil? current-score)
                                                            (< next-possible-score current-score))
                                     next-score (if next-score-better?
                                                  next-possible-score
                                                  current-score)
                                     next-move (if next-score-better?
                                                 next-possible-move
                                                 (:best-move current-stuff))
                                     next-beta (if (or (nil? current-beta)
                                                       (< next-score current-beta))
                                                 next-score
                                                 current-beta)]
                                 {:current-score next-score
                                  :best-move next-move
                                  :alpha current-alpha
                                  :beta next-beta}))]
              next-stuff))
          {:current-score nil
           :best-move nil
           :alpha higher-alpha
           :beta higher-beta}
          (get-possible-moves board)))

(defn maximise
  [board marker depth higher-alpha higher-beta]
  (reduce (fn [current-stuff next-possible-move]
            (let [simulated-board (place-mark board next-possible-move marker)
                  current-alpha (:alpha current-stuff)
                  current-beta (:beta current-stuff)
                  next-stuff (if (and (some? current-alpha)
                                      (some? current-beta)
                                      (> current-alpha current-beta))
                               current-stuff
                               (let [next-possible-score (if (terminal-state? simulated-board)
                                                          (let [winner (winner? simulated-board)]
                                                            (cond
                                                            (nil? winner) tied-score
                                                            (= marker winner) (- maximum-depth depth)
                                                            :else (- depth maximum-depth)))
                                                          (:current-score (minimise simulated-board 
                                                                                    (get-opp-marker marker) 
                                                                                    (inc depth) 
                                                                                    current-alpha 
                                                                                    current-beta)))
                                     current-score (:current-score current-stuff)
                                     next-score-better? (or (nil? current-score)
                                                            (> next-possible-score current-score))
                                     next-score (if next-score-better?
                                                  next-possible-score
                                                  current-score)
                                     next-move (if next-score-better?
                                                 next-possible-move
                                                 (:best-move current-stuff))
                                     next-alpha (if (or (nil? current-alpha)
                                                       (> next-score current-alpha))
                                                 next-score
                                                 current-alpha)]
                                 {:current-score next-score
                                  :best-move next-move
                                  :alpha next-alpha
                                  :beta current-beta}))]
              next-stuff))
          {:current-score nil
           :best-move nil
           :alpha higher-alpha
           :beta higher-beta}
          (get-possible-moves board)))

(defn get-tile-from-computer
  [board marker depth maximiser]
  (:best-move (maximise board
                        marker
                        depth
                        -1000
                        1000)))
