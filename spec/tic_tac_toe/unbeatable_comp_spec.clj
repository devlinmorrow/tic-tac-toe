(ns tic-tac-toe.unbeatable-comp-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.unbeatable-comp :refer :all]))

(describe "get-tile-unbeatable-comp"
          (it "gets winning tile position - 1"

              ; O X O
              ; O O X
              ; X 8 9 
              ; optimum move: 9 (position 8)

              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
                (should= 8
                         (minimax board player-two-mark player-one-mark))))

          (it "gets winning tile position - 2"

              ; O X O
              ; 4 5 X
              ; X O O 
              ; optimum move: 5 (position 4)

              (let [board [player-two-mark player-one-mark player-two-mark
                           "4" "5" player-one-mark
                           player-one-mark player-two-mark player-two-mark]]
                (should= 4
                         (minimax board player-two-mark player-one-mark))))

          (it "gets winning tile position - 3"

              ; O 2 X
              ; X 5 6
              ; X O O 
              ; optimum move: 5 (position 4)

              (let [board [player-two-mark "2" player-one-mark 
                           player-one-mark "5" "6"
                           player-one-mark player-two-mark player-two-mark]]
                (should= 4
                         (minimax board player-two-mark player-one-mark))))

          (it "gets winning tile position - 4"

              ; X X 3
              ; 4 X 6
              ; O 8 O 
              ; optimum move: 8 (position 7)

              (let [board [player-one-mark player-one-mark "3"
                           "4" player-one-mark "6"
                           player-two-mark "8" player-two-mark]]
                (should= 7
                         (minimax board player-two-mark player-one-mark))))

          (it "gets winning tile position - 5"

              ; X 2 O
              ; O 5 6
              ; O X X 
              ; optimum move: 5 (position 4)

              (let [board [player-one-mark "2" player-two-mark
                           player-two-mark "5" "6"
                           player-two-mark player-one-mark player-one-mark]]
                (should= 4
                         (minimax board player-two-mark player-one-mark))))

          (it "gets winning tile position - 6 - testing depth so that it loses in as many turns as possible"

              ; 1 X 3
              ; 4 5 X
              ; O O X 
              ; optimum move: 3 (position 2)

              (let [board ["1" player-one-mark "3"
                           "4" "5" player-one-mark
                           player-two-mark player-two-mark player-one-mark]]
                (should= 2
                         (minimax board player-two-mark player-one-mark))))

          (it "gets winning tile position - 7 - player one is minimaxer"

              ; O O X
              ; 4 X 6
              ; 7 8 9 
              ; optimum move: 7 (position 6)

              (let [board [player-two-mark player-two-mark player-one-mark
                           "4" player-one-mark "6"
                           "7" "8" "9"]]
                (should= 6
                         (minimax board player-one-mark player-two-mark)))))
