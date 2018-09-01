(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer :all]
            [tic-tac-toe.dumb-comp :refer :all]
            [tic-tac-toe.human-player :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.messages :refer :all]))

(defn start-message
  []
  (do 
    (println welcome-message)
    (present-board (make-initial-board 3))))

(defn switch-player
  [current-player player-one player-two]
  (if (= player-one current-player) 
    player-two
    player-one))

(defn get-tile
  [player-type]
  (if (= player-one-mark player-type)
    get-tile-from-human
    get-tile-from-dumb-comp))

(defn make-move
  [current-board current-player]
  (place-mark current-board ((get-tile (:mark current-player)) current-board) (:mark current-player)))

(defn play-all-turns
  [player-one player-two]
  (loop [current-board (make-initial-board 3) 
         current-player player-two]
    (cond 
      (winner? current-board) (println (winner-message (:mark current-player)))
      (is-full? current-board) (println draw-message)
      :else (do 
              (let [current-player (switch-player current-player player-one player-two)
                    current-board (make-move current-board current-player)]
                (newline)
                (println picked-tile-message)
                (present-board current-board)
                (newline)
                (recur current-board current-player))))))

(defn run
  [player-one player-two]
  (do
    (start-message)
    (play-all-turns player-one player-two)))
