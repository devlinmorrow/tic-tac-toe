(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer :all]
            [tic-tac-toe.human-player :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.messages :refer :all]))

(defn start-message
  []
  (do 
    (println welcome-message)
    (present-board (make-initial-board 3))))

(defn switch-player
  [mark]
  (if (= mark player-one-mark) player-two-mark
    player-one-mark))

(defn make-move
  [current-board current-player]
  (place-mark current-board (- (get-tile-number current-board) 1) current-player))

(defn play-all-turns
  []
  (loop [current-board (make-initial-board 3) 
         current-player player-two-mark]
    (cond 
      (winner? current-board) (println (winner-message current-player))
      (is-full? current-board) (println draw-message)
      :else (do 
              (let [current-player (switch-player current-player)
                    current-board (make-move current-board current-player)]
                (newline)
                (println picked-tile-message)
                (present-board current-board)
                (newline)
                (recur current-board current-player))))))

(defn run
  []
  (do
    (start-message)
    (play-all-turns)))
