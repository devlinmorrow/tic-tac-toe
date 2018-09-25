(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.board :refer [tile-marked?
                                       not-in-range?]]
            [tic-tac-toe.messages :refer [already-picked-message
                                          ask-for-tile-choice
                                          choice-out-of-range-message
                                          not-integer-message]]
            [tic-tac-toe.ui :refer [attempt-get-number
                                    clear-screen
                                    delay-in-secs
                                    format-board-cli
                                    get-string-from-user
                                    send-message]]))


(defn- invalid-data-mechs 
  [invalid-data-reason delay-time board]
  (send-message invalid-data-reason)
  (delay-in-secs delay-time)
  (clear-screen)
  (send-message (format-board-cli board)))

(defn- get-user-choice 
  [board delay-time]
  (newline)
  (send-message ask-for-tile-choice)
  (let [picked-tile (attempt-get-number)]
    (if (nil? picked-tile)
      (do
        (invalid-data-mechs not-integer-message delay-time board)
        (get-user-choice board delay-time))
      (- picked-tile 1))))

(defn get-tile-from-user
  [board delay-time]
  (loop [tile-choice (get-user-choice board delay-time)]
    (cond
      (tile-marked? board tile-choice)
      (do
        (invalid-data-mechs already-picked-message delay-time board)
        (recur (get-user-choice board delay-time)))
      (not-in-range? board tile-choice)
      (do
        (invalid-data-mechs choice-out-of-range-message delay-time board)
        (recur (get-user-choice board delay-time)))
      :else tile-choice)))
