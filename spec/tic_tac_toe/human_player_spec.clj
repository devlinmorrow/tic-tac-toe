(ns tic-tac-toe.human-player-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.human-player :refer :all]
            [tic-tac-toe.marks :refer [player-one-mark]]))

(describe "get-tile-from-human"
          (it "gets tile position on second attempt, when first is already marked"
              (let [marked-tile-choice "3"
                    unmarked-tile-choice "9"
                    final-position-picked 8]
                (should= final-position-picked
                         (with-in-str (str marked-tile-choice "\n" unmarked-tile-choice)
                             (get-tile-number (assoc empty-board 2 player-one-mark)))))))
