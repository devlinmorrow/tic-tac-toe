(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.messages :refer :all]))

(defn get-user-tile-choice
  [board]
  (do
    (println ask-for-choice)
    (read-line)))

(defn get-tile-from-human
  [board]
  (- (read-string (get-user-tile-choice board)) 1))
