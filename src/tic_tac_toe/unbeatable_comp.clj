(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer [get-indices-empty-tiles
                                       place-mark
                                       terminal-state?
                                       winner?]]
            [tic-tac-toe.marks :refer :all]))

(def tie 0)
(def maximum-depth 10)

(defn- get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(defn- evaluate-result
  [board marker perspective depth]
  (let [winner (winner? board)]
    (cond
      (= winner marker) (* (- maximum-depth depth) perspective)
      (nil? winner) tie
      :else (* (- depth maximum-depth) perspective))))

(declare choose-best-space)

(defn- simulate-next-move
  [board marker perspective depth]
  (let [opp-marker (get-opp-marker marker)]
    (place-mark board
                (choose-best-space board opp-marker depth)
                opp-marker)))

(defn- score-move
  [board marker perspective depth]
  (if (terminal-state? board)
    (evaluate-result board marker perspective depth)
    (recur (simulate-next-move board marker perspective (inc depth))
           (get-opp-marker marker)
           (* -1 perspective)
           (inc depth))))

(defn- score-moves
  [board empty-indices marker depth]
  (map #(score-move (place-mark board % marker)
                    marker
                    1
                    depth)
       empty-indices))

(defn- make-indices-scores-map
  [board empty-indices marker depth]
  (zipmap empty-indices
          (score-moves board 
                       empty-indices 
                       marker 
                       depth)))

(defn- get-index-max-score
  [idx-scores-map]
  (key (first (sort-by val > idx-scores-map))))

(defn choose-best-space
  [board marker depth]
  (get-index-max-score (make-indices-scores-map board
                                                (get-indices-empty-tiles board)
                                                marker
                                                depth)))
