(ns tic-tac-toe.human-player-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.human-player :refer :all]))

(describe "get-user-tile-choice"
          (it "asks user for tile number selection"
              (should= "3"
                       (with-in-str "3"
                         (get-user-tile-choice [1 2 3 4 5 6 7 8 9])))))

(describe "get-tile-number"
          (it "gets tile number"
              (should= 3
                       (with-in-str "3"
                         (get-tile-number [1 2 3 4 5 6 7 8 9])))))
