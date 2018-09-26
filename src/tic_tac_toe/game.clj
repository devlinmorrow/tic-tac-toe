(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer [make-initial-board
                                       place-mark
                                       winner?
                                       is-full?]]
            [tic-tac-toe.unbeatable-comp :refer [get-tile-from-comp]]
            [tic-tac-toe.human-player :refer [get-tile-from-user]]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.messages :refer [welcome-message
                                          picked-tile-message
                                          winner-message
                                          draw-message]]
            [tic-tac-toe.cli-ui :refer [clear-screen
                                        delay-in-secs
                                        draw-display
                                        format-board-cli
                                        present-move
                                        send-message
                                        starting-display
                                        win-display]]))

(def starting-depth 0)

(defn- switch-player
  [current-player player-one player-two]
  (if (= player-one current-player) 
    player-two
    player-one))

(defn- get-tile-number
  [player board delay-time]
  (if (= :human (:type player))
    (get-tile-from-user board delay-time)
    (get-tile-from-comp board (:mark player) starting-depth)))

(defn- make-move
  [current-board current-player delay-time]
  (place-mark current-board 
              (get-tile-number current-player current-board delay-time) 
              (current-player :mark)))

(defn- play-all-turns
  [player-one player-two board delay-time]
  (loop [current-player player-one
         current-board board]
    (let [current-board (make-move current-board current-player delay-time)]
      (present-move current-board delay-time)
      (cond
        (winner? current-board) (win-display current-player)
        (is-full? current-board) (draw-display)
        :else (recur (switch-player current-player player-one player-two)
                     current-board)))))

(defn run-game
  [players board delay-time]
  (starting-display board)
  (play-all-turns (first players) (second players) board delay-time))
