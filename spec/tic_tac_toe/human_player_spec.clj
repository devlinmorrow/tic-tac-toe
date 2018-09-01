(ns tic-tac-toe.human-player-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.human-player :refer :all]))

(describe "get-user-tile-choice"
          (it "asks user for tile number selection"
              (should= "3"
                       (with-in-str "3"
                         (get-user-tile-choice empty-board)))))

(describe "get-tile-number"
          (it "gets tile number"
              (should= 3
                       (with-in-str "3"
                         (get-tile-number empty-board)))))
