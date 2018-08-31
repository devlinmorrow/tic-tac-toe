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

(defn make-move
  [current-board]
  (place-mark current-board (- (get-tile-number current-board) 1) player-one-mark))

(defn play-all-turns
  []
  (loop [current-board (make-initial-board 3)]
    (cond 
      (winner? current-board) (println "winner")
      (is-full? current-board) (println draw-message)
      :else (do 
              (let [current-board (make-move current-board)]
                (newline)
                (println picked-tile-message)
                (present-board current-board)
                (newline)
                (recur current-board))))))

(defn run
  []
  (do
    (start-message)
    (play-all-turns)))
