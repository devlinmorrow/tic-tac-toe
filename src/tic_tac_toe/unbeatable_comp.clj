(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer :all]
            [tic-tac-toe.marks :refer :all]))

(def loss -1)
(def tie 0)
(def win 1)

(defn get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(defn evaluate-result
  [board marker score]
  (let [winner (winner? board)]
    (cond
          (= winner marker) score
          (nil? winner) tie
          :else (* loss score))))

(declare choose-best-space)

(defn simulate-next-move
  [board marker score]
  (let [opp-marker (get-opp-marker marker)]
    (place-mark board
                (choose-best-space board opp-marker)
                opp-marker)))

(defn score-move
  [board marker score]
  (if (terminal-state? board)
    (evaluate-result board marker score)
    (recur (simulate-next-move board marker score)
           (get-opp-marker marker)
           (* loss score))))

(defn score-moves
  [board empty-indices marker]
  (map #(score-move (place-mark board % marker)
                    marker
                    win)
       empty-indices))

(defn create-idx-scores-map
  [board empty-indices marker]
  (zipmap empty-indices
          (score-moves board empty-indices marker)))

(defn pick-max-score-idx
  [idx-scores-map]
  (key (first (sort-by val > idx-scores-map))))

(defn choose-best-space
  [board marker]
  (pick-max-score-idx (create-idx-scores-map board
                                             (get-indices-empty-tiles board)
                                             marker)))
