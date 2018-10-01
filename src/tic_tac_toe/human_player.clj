(ns tic-tac-toe.human-player
  (:require [tic-tac-toe.board :refer [tile-marked?
                                       not-in-range?]]
            [tic-tac-toe.messages :refer [already-picked-message
                                          ask-for-tile-choice
                                          choice-out-of-range-message
                                          not-integer-message]]
            [tic-tac-toe.cli-ui :refer [attempt-get-number
                                        clear-screen
                                        delay-in-secs
                                        format-board-cli
                                        get-string-from-user
                                        get-valid-number-with-board
                                        invalid-data-prompt]]))

(defn- get-tile-choice 
  [board delay-time]
  (let [picked-tile (get-valid-number-with-board ask-for-tile-choice 
                                                 board
                                                 delay-time)]
    (- picked-tile 1)))

(defn get-tile-from-user
  [board delay-time]
  (loop [tile-choice (get-tile-choice board delay-time)]
    (cond
      (tile-marked? board tile-choice)
      (do
        (invalid-data-prompt already-picked-message delay-time)
        (recur (get-tile-choice board delay-time)))
      (not-in-range? board tile-choice)
      (do
        (invalid-data-prompt choice-out-of-range-message delay-time)
        (recur (get-tile-choice board delay-time)))
      :else tile-choice)))
