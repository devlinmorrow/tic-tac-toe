(ns tic-tac-toe.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.marks :refer :all]))

(describe "switch-player"
          (it "returns player one mark if currently player two"
              (should= player-one-mark
                       (switch-player player-two-mark)))

          (it "returns player two mark if currently player two"
              (should= player-two-mark
                       (switch-player player-one-mark))))

(describe "make-move"
          (it "asks current player for tile number and places mark on board"
              (let [empty-board (into [] (map str (range 1 10)))]
                (should= [player-one-mark "2" "3" "4" "5" "6" "7" "8" "9"]
                         (with-in-str "1" (make-move empty-board player-one-mark))))))
