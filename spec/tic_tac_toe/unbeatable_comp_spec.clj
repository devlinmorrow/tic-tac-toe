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
              ; optimum move: position 9 (tile 8)

              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
                (print (get-indices-empty-tiles board))
                (print (build-scores board player-two-mark))
                (should= 8
                         (get-tile-unbeatable-comp board player-two-mark player-one-mark)))))

(describe "get-score"
          (it "returns 0 when no winner"
              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
                (should= 0
                         (get-score board 7 player-two-mark))))

          (it "returns 10 when winner"
              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
                (should= 10
                         (get-score board 8 player-two-mark))))

          (it "finds index of max scsore"
              (should= 2
                       (get-index-of-max-score [2 4 9 4 2])))

          (it "returns tile choice at best score"
              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
              (should= 9
                       (match-best-score-to-tile [8 9] [0 10])))))
