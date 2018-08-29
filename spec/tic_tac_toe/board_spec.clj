(ns tic-tac-toe.board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]))

(describe "board"
          (it "displays a 3x3 board"
              (let [example-board [1 2 3 4 5 6 7 8 9]]
                (should= "1 2 3 \n4 5 6 \n7 8 9  \n"
                         (with-out-str (present-board 3 example-board))))))
