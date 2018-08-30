(ns tic-tac-toe.board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.marks :refer :all]))

(def example-board (into [] (map str (range 1 10))))

(describe "make initial board"
          (it "makes a board according to the grid size provided"
              (should= example-board
                       (make-initial-board 3))))

(describe "present-board"
          (it "displays a 3x3 board"
              (should= "1 2 3 \n4 5 6 \n7 8 9  \n"
                       (with-out-str (present-board example-board)))))

(describe "place-mark"
          (it "replaces element of grid with given mark"
              (should= (assoc example-board 2 "X")
                       (place-mark (make-initial-board 3) 2 "X"))))

(describe "is-full?"
          (it "is true when board is full of mix of player one and two marks"
              (let [full-board (conj (vec (repeat 8 player-one-mark)) player-two-mark)]
                    (should
                      (is-full? full-board))))

                    (it "is false when board is not full"
                        (should= false
                                 (is-full? example-board))))
