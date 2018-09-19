(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.board :refer [tile-marked?
                                       not-in-range?]]
            [tic-tac-toe.messages :refer [already-picked-message
                                          choice-out-of-range-message]]
            [tic-tac-toe.ui :refer [get-user-tile-choice
                                    send-message]]))

(defn- get-human-choice []
  (- (get-user-tile-choice) 1))

(defn get-tile-from-human
  [board]
  (loop [tile-choice (get-human-choice)]
    (cond 
      (tile-marked? board tile-choice)
      (do
        (send-message already-picked-message)
        (recur (get-human-choice)))
      (not-in-range? board tile-choice)
      (do
        (send-message choice-out-of-range-message)
        (recur (get-human-choice)))
      :else tile-choice)))
