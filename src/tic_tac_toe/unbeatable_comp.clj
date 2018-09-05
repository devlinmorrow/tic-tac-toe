(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer :all]))

(declare score-moves)

(def max-value-score 10)
(def tie-score 0)

(defn maximising-level?
  [depth]
  (boolean (= (mod depth 2) 0)))

(defn get-min
  [moves]
  (val (apply min-key val moves)))

(defn get-max
  [moves]
  (val (apply max-key val moves)))

(defn get-minimax-score
  [depth moves]
  (if (maximising-level? depth)
    (get-max moves)
    (get-min moves)))

(defn get-score-for-winner
  [board minimaxer depth]
    (if (= (winner? board) minimaxer)
      (- max-value-score depth)
      (- (- max-value-score depth))))

(defn get-score
  [board minimaxer opponent depth]
  (cond 
    (winner? board) (get-score-for-winner board minimaxer depth)
    (is-full? board) tie-score
    :else (get-minimax-score depth (score-moves board opponent minimaxer depth))))

(defn get-mark-at-depth
  [depth minimaxer opponent]
  (if (maximising-level? depth)
    minimaxer
    opponent))

(defn score-moves 
  [board minimaxer opponent depth]
  (let [empty-tiles (get-indices-empty-tiles board)
        scores (map (fn [empty-tile] get-score (place-mark board empty-tile (get-mark-at-depth depth minimaxer opponent)) minimaxer opponent (inc depth)) empty-tiles)]
    (zipmap empty-tiles scores)))

(defn get-best-move
  [moves]
  (key (apply max-key val moves)))

(defn minimax
  [board minimaxer opponent]
  (get-best-move (score-moves board minimaxer opponent 0)))
