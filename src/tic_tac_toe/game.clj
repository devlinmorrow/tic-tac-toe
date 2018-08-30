(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer :all]
            [tic-tac-toe.human-player :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.messages :refer :all]))

(defn start-message
  []
  (do 
    (println welcome-message)
    (present-board 3 initial-board)))

(defn make-move
  []
  (present-board 3 (place-mark (- (get-tile-number) 1) player-one-mark)))

(defn run
  []
  (do
     (start-message)
     (make-move)))
