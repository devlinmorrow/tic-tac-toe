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
  [board marker depth initial-alpha initial-beta]
  (reduce (fn [best-vals next-possible-move]
            (let [simulated-board (place-mark board next-possible-move marker)
                  best-alpha (:best-alpha best-vals)
                  best-beta (:best-beta best-vals)]
              (if (> best-alpha best-beta)
                best-vals
                (let [next-possible-score (if (terminal-state? simulated-board)
                                            (let [winner (winner? simulated-board)]
                                              (cond
                                                (nil? winner) tied-score
                                                (= marker winner) (- depth maximum-depth)
                                                :else (- maximum-depth depth)))
                                            (:best-score (maximise simulated-board 
                                                                      (get-opp-marker marker) 
                                                                      (inc depth) 
                                                                      best-alpha 
                                                                      best-beta)))
                      best-score (:best-score best-vals)
                      next-score-lower? (or (nil? best-score)
                                            (< next-possible-score best-score))
                      updated-score (if next-score-lower?
                                      next-possible-score
                                      best-score)
                      updated-move (if next-score-lower?
                                     next-possible-move
                                     (:best-move best-vals))
                      updated-beta (if (or (nil? best-beta)
                                           (< updated-score best-beta))
                                     updated-score
                                     best-beta)]
                  {:best-score updated-score
                   :best-move updated-move
                   :best-alpha best-alpha
                   :best-beta updated-beta}))))
          {:best-score nil
           :best-move nil
           :best-alpha initial-alpha
           :best-beta initial-beta}
          (get-possible-moves board)))

(defn maximise
  [board marker depth initial-alpha initial-beta]
  (reduce (fn [best-vals next-possible-move]
            (let [simulated-board (place-mark board next-possible-move marker)
                  best-alpha (:best-alpha best-vals)
                  best-beta (:best-beta best-vals)]
              (if (> best-alpha best-beta)
                best-vals
                (let [next-possible-score (if (terminal-state? simulated-board)
                                            (let [winner (winner? simulated-board)]
                                              (cond
                                                (nil? winner) tied-score
                                                (= marker winner) (- maximum-depth depth)
                                                :else (- depth maximum-depth)))
                                            (:best-score (minimise simulated-board 
                                                                      (get-opp-marker marker) 
                                                                      (inc depth) 
                                                                      best-alpha 
                                                                      best-beta)))
                      best-score (:best-score best-vals)
                      next-score-greater? (or (nil? best-score)
                                              (> next-possible-score best-score))
                      updated-score (if next-score-greater?
                                      next-possible-score
                                      best-score)
                      updated-move (if next-score-greater?
                                     next-possible-move
                                     (:best-move best-vals))
                      updated-alpha (if (or (nil? best-alpha)
                                            (> updated-score best-alpha))
                                      updated-score
                                      best-alpha)]
                  {:best-score updated-score
                   :best-move updated-move
                   :best-alpha updated-alpha
                   :best-beta best-beta}))))
          {:best-score nil
           :best-move nil
           :best-alpha initial-alpha
           :best-beta initial-beta}
          (get-possible-moves board)))

(defn get-tile-from-computer
  [board marker depth maximiser]
  (:best-move (maximise board
                        marker
                        depth
                        -1000
                        1000)))
