(ns tic-tac-toe.board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.marks :refer :all]))

(def example-board (into [] (map str (range 1 10))))

(describe "make initial board"
          (it "makes a board according to the grid size provided"
              (should= example-board
                       (make-initial-board))))

(describe "present-board"
          (it "displays a 3x3 board"
              (should= "1 2 3 \n4 5 6 \n7 8 9  \n"
                       (with-out-str (present-board example-board)))))

(describe "place-mark"
          (it "replaces element of grid with given mark"
              (should= (assoc example-board 2 "X")
                       (place-mark (make-initial-board) 2 "X"))))

(describe "is-full?"
          (it "is true when board is full of mix of player one and two marks"
              (let [full-board (conj (vec (repeat 8 player-one-mark)) player-two-mark)]
                (should (is-full? full-board))))

          (it "is false when board is not full"
              (should-not (is-full? example-board))))

(describe "winner?"
          (it "(top row) returns player one mark when player one wins"
              (should= player-one-mark
                      (winner? [player-one-mark player-one-mark player-one-mark 
                                        "4" "5" "6" "7" "8" "9"])))

          (it "(mid row) returns player one mark when player one wins"
              (should= player-one-mark
                      (winner? ["1" "2" "3" player-one-mark player-one-mark 
                                player-one-mark "7" "8" "9"])))
; ["1" "2" "3" "4" "5" "6" "7" "8" "9"]
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
              (should-not (top-row-winner? example-board))))
