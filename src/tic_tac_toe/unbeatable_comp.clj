(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer :all]))

(defn score-win
  [board optimising-player]
  (if (= (winner? board) optimising-player) 
    10
    -10))

(defn score-terminal-board
  [board optimising-player]
  (if (winner? board) 
    (score-win board optimising-player)
    0))

(defn emulate-move
  [board move optimising-player]
  (place-mark board move optimising-player))

(defn get-score
  [board move optimising-player]
  (let [emulated-board (emulate-move board move optimising-player)]
    (score-terminal-board emulated-board optimising-player)))

(defn build-scores
  [board optimising-player]
  (let [empty-tiles (get-indices-empty-tiles board)]
    (into [] (map (fn [empty-tile] (get-score board empty-tile optimising-player)) empty-tiles))))

(defn get-index-of-max-score
  [scores]
  (.indexOf scores (apply max scores)))

(defn match-best-score-to-tile
  [empty-tiles scores]
  (get empty-tiles (get-index-of-max-score scores)))

(defn get-tile-unbeatable-comp
  [board optimising-player opponent]
  (let [empty-tiles (get-indices-empty-tiles board)
        scores (build-scores board optimising-player)]
    (match-best-score-to-tile empty-tiles scores)))

