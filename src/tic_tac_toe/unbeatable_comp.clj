(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer [get-possible-moves
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

(defn- score-terminal-board
  [board marker perspective depth minimaxer]
  (let [winner (winner? board)]
    (cond
      (= winner minimaxer) (* (- maximum-depth depth) perspective)
      (nil? winner) tie
      :else (* (- depth maximum-depth) perspective))))

(defn- at-max-depth?
  [depth]
  (> -1 (- maximum-depth depth)))

(declare get-tile-from-computer)

(defn- get-best-next-board-for-marker
  [board marker perspective depth minimaxer]
  (let [opp-marker (get-opp-marker marker)]
    (place-mark board
                (get-tile-from-computer board opp-marker depth minimaxer)
                opp-marker)))

(defn- score-move
  [board marker perspective depth minimaxer]
  (if (or (terminal-state? board) (at-max-depth? depth))
    (score-terminal-board board marker perspective depth minimaxer)
    (recur (get-best-next-board-for-marker board marker perspective (inc depth) minimaxer)
           (get-opp-marker marker)
           (* -1 perspective)
           (inc depth)
           move
           minimaxer)))

(defn- score-moves
  [board possible-moves marker depth minimaxer]
  (map #(score-move (place-mark board % marker)
                    marker
                    1
                    depth
                    %
                    minimaxer)
       possible-moves))

(defn- get-moves-to-scores
  [board possible-moves marker depth minimaxer]
  (zipmap possible-moves
          (score-moves board 
                       possible-moves 
                       marker 
                       depth
                       minimaxer)))

(defn- get-index-max-score
  [idx-scores-map]
 (last (apply max-key first idx-scores-map)))

(defn get-tile-from-computer
  [board marker depth]
  (get-index-max-score (score-moves board
                                    (get-indices-empty-tiles board)
                                    marker
                                    depth)))

(defn get-tile-from-computer
  [board marker depth minimaxer]
  (let [moves-to-scores (get-moves-to-scores board
                                             (get-possible-moves board)
                                             marker
                                             depth
                                             minimaxer)]
    (if (= marker minimaxer)
      (get-max-move moves-to-scores)
      (get-min-move moves-to-scores))))
