(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer [get-possible-moves
                                       place-mark
                                       terminal-state?
                                       winner?]]
            [tic-tac-toe.marks :refer :all]))

(def tie 0)
(def maximum-depth 10)

(defn maximising-depth?
  [depth]
  (= 0 (mod depth 2)))

(defn- get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(defn- get-min-scoring-move
  [idx-scores-map]
  (apply min-key first idx-scores-map))

(defn- get-max-scoring-move
  [idx-scores-map]
  (apply max-key first idx-scores-map))

(defn- score-terminal-board
  [board depth]
  (let [winner (winner? board)]
    (cond
      (= winner minimaxer) (- maximum-depth depth)
      (nil? winner) tie
      :else (- depth maximum-depth))))

(declare maximise)
(declare minimise)

(defn- score-move
  [board marker depth move minimaxer]
  (if (terminal-state? board)
    [(score-terminal-board board depth minimaxer) move]
    (if (= marker minimaxer)
      (minimise board
                (get-opp-marker marker)
                (inc depth)
                minimaxer)
      (maximise board
                (get-opp-marker marker)
                (inc depth)
                minimaxer))))

(defn minimise
  [board marker depth minimaxer]
  (let [moves-to-scores (map #(score-move (place-mark board % marker)
                                          marker
                                          depth
                                          %
                                          minimaxer)
                             (get-possible-moves board))]
    (get-min-scoring-move moves-to-scores)))

(defn maximise
  [board marker depth minimaxer]
  (let [moves-to-scores (map #(score-move (place-mark board % marker)
                                          marker
                                          depth
                                          %
                                          minimaxer)
                             (get-possible-moves board))]
    (get-max-scoring-move moves-to-scores)))

(defn get-tile-from-computer
  [board marker depth minimaxer]
  (last (maximise board marker depth minimaxer)))
