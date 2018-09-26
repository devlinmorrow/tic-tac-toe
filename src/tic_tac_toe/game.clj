(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer [make-initial-board
                                       place-mark
                                       winner?
                                       is-full?]]
            [tic-tac-toe.unbeatable-comp :refer [choose-best-space]]
            [tic-tac-toe.human-player :refer [get-tile-from-user]]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.messages :refer [welcome-message
                                          picked-tile-message
                                          winner-message
                                          draw-message]]
            [tic-tac-toe.cli-ui :refer [clear-screen
                                    delay-in-secs
                                    format-board-cli
                                    send-message]]))

(defn- starting-prompt
  [board]
  (send-message welcome-message)
  (send-message (format-board-cli board)))

(defn- switch-player
  [current-player player-one player-two]
  (if (= player-one current-player) 
    player-two
    player-one))

(defn- get-tile-number
  [player board]
  (if (= :human (:type player))
    (get-tile-from-user board 1)
    (choose-best-space board (:mark player) 0)))

(defn- make-move
  [current-board current-player]
  (place-mark current-board 
              (get-tile-number current-player current-board) 
              (current-player :mark)))

(defn- present-move 
  [board delay-time]
      (clear-screen)
  (newline)
  (send-message picked-tile-message)
  (newline)
  (send-message (format-board-cli board))
  (delay-in-secs delay-time))

(defn- play-all-turns
  [player-one player-two board delay-time]
  (loop [current-player player-one
         current-board board]
    (let [current-board (make-move current-board current-player)]
      (present-move current-board delay-time)
      (cond
        (winner? current-board) 
        (do
          (newline)
          (send-message (winner-message (:mark current-player)))
          (newline))
        (is-full? current-board)         
        (do
          (newline)
          (send-message draw-message)
          (newline))
        :else (recur (switch-player current-player player-one player-two)
                     current-board)))))

(defn run-game
  [player-one player-two board delay-time]
  (starting-prompt board)
  (play-all-turns player-one player-two board delay-time))
