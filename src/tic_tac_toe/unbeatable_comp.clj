(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer [get-possible-moves
                                       place-mark
                                       terminal-state?
                                       winner?]]
            [tic-tac-toe.marks :refer :all]))

(def tied-score 0)
(def maximum-depth 10)

(defn- get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(declare maximise)

(defn minimise
  [board marker depth]
  (reduce (fn [current-score-to-move next-possible-move]
            (let [simulated-board (place-mark board next-possible-move marker)
                  next-score (if (terminal-state? simulated-board)
                               (let [winner (winner? simulated-board)]
                                 (cond 
                                   (nil? winner) tied-score
                                   (= marker winner) (- depth maximum-depth)
                                   :else (- maximum-depth depth)))
                               (first (maximise simulated-board (get-opp-marker marker) (inc depth))))
                  current-min-score (first current-score-to-move)]
              (if (or (nil? current-min-score)
                      (< next-score current-min-score))
                [next-score next-possible-move]
                current-score-to-move)))
          [nil nil]
          (get-possible-moves board)))

(defn maximise
  [board marker depth]
  (reduce (fn [current-score-to-move next-possible-move]
            (let [simulated-board (place-mark board next-possible-move marker)
                  next-score (if (terminal-state? simulated-board)
                               (let [winner (winner? simulated-board)]
                                 (cond 
                                   (nil? winner) tied-score
                                   (= marker winner) (- maximum-depth depth)
                                   :else (- depth maximum-depth)))
                               (first (minimise simulated-board (get-opp-marker marker) (inc depth))))
                  current-max-score (first current-score-to-move)]
              (if (or (nil? current-max-score)
                      (> next-score current-max-score))
                [next-score next-possible-move]
                current-score-to-move)))
          [nil nil]
          (get-possible-moves board)))

(defn get-tile-from-computer
  [board marker]
  (last (maximise board marker 0)))
