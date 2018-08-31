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
