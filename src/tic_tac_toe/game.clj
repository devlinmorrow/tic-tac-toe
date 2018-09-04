(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer [make-initial-board
                                       place-mark
                                       winner?
                                       is-full?]]
            [tic-tac-toe.human-player :refer [get-tile-number]]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.messages :refer [welcome-message
                                          picked-tile-message
                                          winner-message
                                          draw-message]]
            [tic-tac-toe.ui :refer [format-board-cli
                                    send-message]]))

(defn start-message
  []
  (send-message welcome-message)
  (send-message (format-board-cli (make-initial-board))))

(defn switch-player
  [mark]
  (if (= mark player-one-mark) player-two-mark
    player-one-mark))

(defn make-move
  [current-board current-player]
  (place-mark current-board (get-tile-number current-board) current-player))

(defn present-move 
  [board]
  (newline)
  (send-message picked-tile-message)
  (send-message (format-board-cli board)))

(defn play-all-turns
  []
  (loop [current-board (make-initial-board) 
         current-player player-one-mark]
    (let [current-board (make-move current-board current-player)]
      (present-move current-board)
      (cond
        (winner? current-board) (send-message (winner-message current-player))
        (is-full? current-board) (send-message draw-message)
        :else (recur current-board (switch-player current-player))))))

(defn run
  []
  (start-message)
  (play-all-turns))
