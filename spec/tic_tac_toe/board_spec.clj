(ns tic-tac-toe.board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.marks :refer :all]))

(def empty-board (into [] (map str (range 1 10))))

(describe "make initial board"
          (it "makes a board according to the grid size provided"
              (should= empty-board
                       (make-initial-board))))

(describe "not-in-range?"
          (it "is false when tile picked is within range"
                (should-not (not-in-range? empty-board 3))))

          (it "is true when tile picked is not within range"
                (should (not-in-range? empty-board 15)))

(describe "tile-marked?"
            (it "is true when tile already marked"
                (let [marked-board (conj (vec (repeat 9 player-one-mark)))]
                  (should (tile-marked? marked-board 0))))

            (it "is false when tile unmarked"
                (should-not (tile-marked? empty-board 0))))

(describe "is-full?"
          (it "is true when board is full of mix of player one and two marks"
              (let [full-board (conj (vec (repeat 8 player-one-mark)) player-two-mark)]
                (should (is-full? full-board))))

          (it "is false when board is not full"
              (should-not (is-full? empty-board))))

(describe "winner?"
          (it "(top row) returns player one mark when player one wins"
              (should= player-one-mark
                       (winner? [player-one-mark player-one-mark player-one-mark 
                                 "4" "5" "6" "7" "8" "9"])))

          (it "(mid row) returns player one mark when player one wins"
              (should= player-one-mark
                       (winner? ["1" "2" "3" player-one-mark player-one-mark 
                                 player-one-mark "7" "8" "9"])))

          (it "(bot row) returns player one mark when player one wins"
              (should= player-one-mark
                       (winner? ["1" "2" "3" "4" "5" "6" player-one-mark 
                                 player-one-mark player-one-mark])))

          (it "(left col) returns player one mark when player one wins"
              (should= player-one-mark
                       (winner? [player-one-mark "2" "3" player-one-mark 
                                 "5" "6" player-one-mark "8" "9"])))

          (it "(mid col) returns player one mark when player one wins"
              (should= player-one-mark
                       (winner? ["1" player-one-mark "3" "4" player-one-mark 
                                 "6" "7" player-one-mark "9"])))

          (it "(right col) returns player one mark when player one wins"
              (should= player-one-mark
                       (winner? ["1" "2" player-one-mark "4" "5" player-one-mark 
                                 "7" "8" player-one-mark])))

          (it "(topleft diag) returns player two mark when player two wins"
              (should= player-two-mark
                       (winner? [player-two-mark "2" "3" "4" player-two-mark 
                                 "6" "7" "8" player-two-mark])))

          (it "(botleft diag) returns player two mark when player two wins"
              (should= player-two-mark
                       (winner? ["1" "2" player-two-mark "4" player-two-mark 
                                 "6" player-two-mark "8" "9"])))

          (it "is false when no winner"
              (should-not (winner? empty-board))))

(describe "terminal-state?"
          (it "is true when there is a winner"
              (let [won-board [player-one-mark player-one-mark player-one-mark
                               "4" "5" "6" "7" "8" "9"]]
                (should (terminal-state? won-board))))

          (it "is true when it is a tie"
              (let [tied-board [player-one-mark player-one-mark player-two-mark
                                player-two-mark player-two-mark player-one-mark
                                player-one-mark player-one-mark player-two-mark]]
                (should (terminal-state? tied-board))))

          (it "is false when not in terminal state"
              (should-not (terminal-state? empty-board))))

(describe "get-indices-empty-tiles"
          (it "returns indices of all empty tiles"
              (let [all-empty (into [] (range 9))]
                    (should= all-empty
                             (get-indices-empty-tiles empty-board))))

          (it "returns indices of only empty tiles"
              (let [ex-board [player-one-mark "2" "3" player-two-mark "5" "6" "7" "8" "9"]
                    ex-indices [1 2 4 5 6 7 8]]
                (should= ex-indices
                         (get-indices-empty-tiles ex-board)))))
