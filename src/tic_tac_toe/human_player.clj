(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.messages :refer [ask-for-choice]]))

(defn get-user-tile-choice
  [board]
  (do
    (println ask-for-choice)
    (read-line)))

(defn get-tile-number 
  [board]
  (read-string (get-user-tile-choice board)))
