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

(defn- get-min-scoring-move
  [idx-scores-map]
  (last (apply min-key first idx-scores-map)))

(defn- get-max-scoring-move
  [idx-scores-map]
  (last (apply max-key first idx-scores-map)))

(defn- at-max-depth?
  [depth]
  (> -1 (- maximum-depth depth)))

(defn- score-terminal-board
  [board marker  depth minimaxer]
  (let [winner (winner? board)]
    (cond
      (= winner minimaxer) (- maximum-depth depth)
      (nil? winner) tie
      :else (- depth maximum-depth))))

(declare get-tile-from-computer)

(defn- get-best-next-board-for-marker
  [board marker depth minimaxer]
  (let [opp-marker (get-opp-marker marker)]
    (place-mark board
                (get-tile-from-computer board opp-marker depth minimaxer)
                opp-marker)))

(defn- score-move
  [board marker depth move minimaxer]
  (if (or (terminal-state? board) (at-max-depth? depth))
    [(score-terminal-board board marker depth minimaxer) move]
    (recur (get-best-next-board-for-marker board marker (inc depth) minimaxer)
           (get-opp-marker marker)
           (inc depth)
           move
           minimaxer)))

(defn- score-moves
  [board possible-moves marker depth minimaxer]
  (map #(score-move (place-mark board % marker)
                    marker
                    depth
                    %
                    minimaxer)
       possible-moves))

(defn get-tile-from-computer
  [board marker depth minimaxer]
  (let [moves-to-scores (score-moves board
                                     (get-possible-moves board)
                                     marker
                                     depth
                                     minimaxer)]
    (if (= marker minimaxer)
      (get-max-scoring-move moves-to-scores)
      (get-min-scoring-move moves-to-scores))))
