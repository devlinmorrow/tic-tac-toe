(ns tic-tac-toe.game-spec (:require [speclj.core :refer :all]
                                    [tic-tac-toe.board-spec :refer [empty-board]]
                                    [tic-tac-toe.game :refer :all]
                                    [tic-tac-toe.marks :refer :all]
                                    [tic-tac-toe.messages :refer [draw-message
                                                                  winner-message]]))

(def delay-time 0)

(describe "run-game"
          (it "contains 'won' when game won (human vs human)"
              (let [won-board [player-one-mark player-one-mark "3"
                               player-two-mark player-two-mark "6"
                               "7" "8" "9"]
                    players [{:type :human :mark "X"} {:type :human :mark "O"}]]
                (should (re-find #"won" (with-out-str 
                                          (with-in-str "3"
                                            (run-game players won-board delay-time)))))))

          (it "contains 'draw' when game drawn (comp vs human)"
              (let [drawn-board [player-one-mark player-two-mark "3"
                                 player-two-mark player-two-mark player-one-mark
                                 player-two-mark player-one-mark player-two-mark]
                    players [{:type :computer :mark "X"} {:type :human :mark "O"}]]
                (should (re-find #"draw" (with-out-str 
                         (run-game players drawn-board delay-time)))))))
