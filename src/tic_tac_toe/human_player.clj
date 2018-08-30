(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.messages :refer :all]))

(defn get-user-tile-choice
  []
  (do
    (println ask-for-choice)
    (read-line)))

(defn get-tile-number
  []
  (read-string (get-user-tile-choice)))
