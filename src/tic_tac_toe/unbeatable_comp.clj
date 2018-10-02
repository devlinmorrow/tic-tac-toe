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

(defn- at-max-depth?
  [depth]
  (> -1 (- maximum-depth depth)))

(declare get-tile-from-computer)

(defn- simulate-next-move
  [board marker perspective depth]
  (let [opp-marker (get-opp-marker marker)]
    (place-mark board
                (get-tile-from-computer board opp-marker depth)
                opp-marker)))

(defn- score-move
  [board marker perspective depth move]
  (if (or (terminal-state? board) (at-max-depth? depth))
    [(evaluate-result board marker perspective depth) move]
    (recur (simulate-next-move board marker perspective (inc depth))
           (get-opp-marker marker)
           (* -1 perspective)
           (inc depth)
           move)))

(defn- score-moves
  [board empty-indices marker depth]
  (map #(score-move (place-mark board % marker)
                    marker
                    1
                    depth
                    %)
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
 (last (apply max-key first idx-scores-map)))

(defn get-tile-from-computer
  [board marker depth]
  (get-index-max-score (score-moves board
                                    (get-indices-empty-tiles board)
                                    marker
                                    depth)))
