(ns tic-tac-toe.dumb-comp
  (:require [tic-tac-toe.board :refer [tile-marked?]]))

(defn get-tile-choice []
  (rand-int 9))

(defn get-tile-from-dumb-comp
  [board]
  (loop [tile-choice (get-tile-choice)]
    (if (tile-marked? board tile-choice)
      (recur (get-tile-choice))
      tile-choice)))
