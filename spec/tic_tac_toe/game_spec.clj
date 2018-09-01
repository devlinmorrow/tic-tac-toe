(ns tic-tac-toe.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.marks :refer :all]))

(describe "switch-player"
          (let [player-one {:type :human :mark "X"} player-two {:type :human :mark "O"}]
            (it "returns player one mark if currently player two"
                (should= player-one
                         (switch-player player-two player-one player-two)))

            (it "returns player two mark if currently player one"
                (should= player-two
                         (switch-player player-one player-one player-two)))))

(describe "make-move"
          (it "asks current player for tile number and places mark on board"
                (should= [player-one-mark "2" "3" "4" "5" "6" "7" "8" "9"]
                         (with-in-str "1" (make-move empty-board player-one-mark)))))
