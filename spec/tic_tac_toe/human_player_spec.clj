(ns tic-tac-toe.human-player-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.human-player :refer :all]
            [tic-tac-toe.marks :refer [player-one-mark]]))

(describe "get-tile-from-user"
          (it "gets tile position on first correct attempt"
              (let [number-choice "9"
                    final-position-picked 8]
                (should= final-position-picked
                         (with-in-str number-choice
                           (get-tile-from-user empty-board 0)))))

          (it "gets tile position on second attempt, when first is not a number"
              (let [nan-choice "3av"
                    number-choice "9"
                    final-position-picked 8]
                (should= final-position-picked
                         (with-in-str (str nan-choice "\n" number-choice)
                           (get-tile-from-user empty-board 0)))))

          (it "gets tile position on second attempt, when first is already marked"
              (let [marked-tile-choice "3"
                    unmarked-tile-choice "9"
                    final-position-picked 8]
                (should= final-position-picked
                         (with-in-str (str marked-tile-choice "\n" unmarked-tile-choice)
                           (get-tile-from-user (assoc empty-board 2 player-one-mark) 0)))))

          (it "gets tile position on second attempt, when first is out of range"
              (let [out-range-choice "13"
                    unmarked-tile-choice "9"
                    final-position-picked 8]
                (should= final-position-picked
                         (with-in-str (str out-range-choice "\n" unmarked-tile-choice)
                           (get-tile-from-user empty-board 0))))))
