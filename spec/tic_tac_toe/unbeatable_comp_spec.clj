(ns tic-tac-toe.unbeatable-comp-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.unbeatable-comp :refer :all]))

(def starting-depth 0)

(describe "get-opp-marker"
          (it "gets player-one-mark when player two passed in"
              (should= player-one-mark
                       (get-opp-marker player-two-mark)))

          (it "gets player-two-mark when player one passed in"
              (should= player-two-mark
                       (get-opp-marker player-one-mark))))

(describe "get-tile-unbeatable-computer"
          (it "gets best tile position - example 1"

              ; 1 X O
              ; O O X
              ; X 8 O 
              ; optimum move: 9 (position 8)

              (let [board ["1" player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" player-two-mark]]
                (should= 0
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - example 2"

              ; O X O
              ; O O X
              ; X 8 9 
              ; optimum move: 9 (position 8)

              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
                (should= 8
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - example 3"

              ; O X O
              ; 4 5 X
              ; X O O 
              ; optimum move: 5 (position 4)

              (let [board [player-two-mark player-one-mark player-two-mark
                           "4" "5" player-one-mark
                           player-one-mark player-two-mark player-two-mark]]
                (should= 4
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - example 4"

              ; O 2 X
              ; X 5 6
              ; X O O 
              ; optimum move: 5 (position 4)

              (let [board [player-two-mark "2" player-one-mark 
                           player-one-mark "5" "6"
                           player-one-mark player-two-mark player-two-mark]]
                (should= 4
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - example 5"

              ; X X 3
              ; 4 X 6
              ; O 8 O 
              ; optimum move: 8 (position 7)

              (let [board [player-one-mark player-one-mark "3"
                           "4" player-one-mark "6"
                           player-two-mark "8" player-two-mark]]
                (should= 7
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - example 6"

              ; X 2 O
              ; O 5 6
              ; O X X 
              ; optimum move: 5 (position 4)

              (let [board [player-one-mark "2" player-two-mark
                           player-two-mark "5" "6"
                           player-two-mark player-one-mark player-one-mark]]
                (should= 4
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - example 7 - testing depth so that it loses in as many turns as possible"

              ; 1 X 3
              ; 4 5 X
              ; O O X 
              ; optimum move: 3 (position 2)

              (let [board ["1" player-one-mark "3"
                           "4" "5" player-one-mark
                           player-two-mark player-two-mark player-one-mark]]
                (should= 2
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - example 8 - player one is minimaxer"

              ; O O X
              ; 4 X 6
              ; 7 8 9 
              ; optimum move: 7 (position 6)

              (let [board [player-two-mark player-two-mark player-one-mark
                           "4" player-one-mark "6"
                           "7" "8" "9"]]
                (should= 6
                         (get-tile-from-computer board player-one-mark starting-depth player-one-mark)))))
