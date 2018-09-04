(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.board :refer [tile-marked?
                                       not-in-range?]]
            [tic-tac-toe.messages :refer [ask-for-choice
                                          already-picked-message
                                          choice-out-of-range-message]]
            [tic-tac-toe.ui :refer [send-message]]))

(defn- get-user-tile-choice
  []
  (newline)
  (send-message ask-for-choice)
  (- (read-string (read-line)) 1))

(defn get-tile-from-human
  [board]
  (loop [tile-choice (get-user-tile-choice)]
    (cond 
      (tile-marked? board tile-choice)
      (do
        (send-message already-picked-message)
        (recur (get-user-tile-choice)))
      (not-in-range? board tile-choice)
      (do
          (send-message choice-out-of-range-message)
          (recur (get-user-tile-choice)))
      :else tile-choice)))
