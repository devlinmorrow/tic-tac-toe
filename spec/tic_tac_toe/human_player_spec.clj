(ns tic-tac-toe.human-player-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.human-player :refer :all]))

(def example-board (into [] (map str (range 1 10))))

(describe "get-user-tile-choice"
          (it "asks user for tile number selection"
              (should= "3"
                       (with-in-str "3"
                         (get-user-tile-choice example-board)))))

(describe "get-tile-from-human"
          (it "gets tile number"
              (should= 2
                       (with-in-str "3"
                         (get-tile-from-human example-board)))))
