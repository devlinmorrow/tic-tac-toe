(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.messages :refer [ask-for-choice
                                          already-picked-message]]
            [tic-tac-toe.board :refer [tile-marked?]]))

(defn- get-user-tile-choice
  []
  (newline)
  (println ask-for-choice)
  (- (read-string (read-line)) 1))

(defn get-tile-number 
  [board]
  (loop [tile-choice (get-user-tile-choice)]
    (if (tile-marked? board tile-choice)
      (do
        (newline)
        (println already-picked-message)
        (recur (get-user-tile-choice)))
      tile-choice)))
