(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]))

(describe "tic-tac-toe"
          (it "displays a 3x3 board"
              (let [example-board "1 2 3\n4 5 6\n7 8 9\n"]
                (should= example-board 
                         (with-out-str (present-board example-board))))))
