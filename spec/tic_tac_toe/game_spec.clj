(ns tic-tac-toe.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.messages :refer [draw-message
                                          winner-message]]))

(def player-one {:type :human :mark "X"})
(def player-two {:type :human :mark "O"})

(describe "run-game"
          (it "does not throw error when game won"
              (let [won-board [player-one-mark player-one-mark "3"
                               player-two-mark player-two-mark "6"
                               "7" "8" "9"]
                    winning-choice "3"
                    delay-time 0]
                (should= nil
                         (with-in-str winning-choice (run-game player-one player-two won-board delay-time)))))

          (it "does not throw error when game drawn"
              (let [drawn-board [player-one-mark player-two-mark "3"
                                 player-two-mark player-two-mark player-one-mark
                                 player-two-mark player-one-mark player-two-mark]
                    drawing-choice "3"
                    delay-time 0]
                (should= nil
                         (with-in-str drawing-choice (run-game player-one player-two drawn-board delay-time))))))
