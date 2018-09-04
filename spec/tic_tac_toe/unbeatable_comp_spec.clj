(ns tic-tac-toe.unbeatable-comp-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.unbeatable-comp :refer :all]))

(describe "get-tile-unbeatable-comp"
          (it "gets winning tile position - 1"

              ; O X O
              ; O O X
              ; X 8 9 
              ; optimum move: position 9 (tile 8)

              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
                (should= 8
                         (get-tile-unbeatable-comp board)))))
