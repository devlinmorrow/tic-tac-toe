(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer [make-initial-board
                                       place-mark
                                       winner?
                                       is-full?]]
            [tic-tac-toe.unbeatable-comp :refer [choose-best-space]]
            [tic-tac-toe.human-player :refer [get-tile-from-human]]
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
  [current-player player-one player-two]
  (if (= player-one current-player) 
    player-two
    player-one))

(defn get-tile-number
  [player board]
  (if (= :human (:type player))
    (get-tile-from-human board)
    (choose-best-space board (:mark player) 0)))

(defn make-move
  [current-board current-player]
  (place-mark current-board 
              (get-tile-number current-player current-board) 
              (current-player :mark)))

(defn present-move 
  [board]
  (newline)
  (send-message picked-tile-message)
  (send-message (format-board-cli board)))

(defn play-all-turns
  [player-one player-two]
  (loop [current-board (make-initial-board) 
         current-player player-one]
    (let [current-board (make-move current-board 
                                   current-player)]
      (present-move current-board)
      (cond
        (winner? current-board) (send-message (winner-message (:mark current-player)))
        (is-full? current-board) (send-message draw-message)
        :else (recur current-board (switch-player current-player player-one player-two))))))

(defn run
  [player-one player-two]
  (start-message)
  (play-all-turns player-one player-two))
