(ns tic-tac-toe.modes-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.modes :refer :all]))

(def delay-time 0)

(describe "select mode"
          (it "returns game mode (i.e. the types of players) on second attempt, first attempt being out of range"
              (let [out-range-choice 9
                    in-range-choice 1
                    correct-game-mode (game-modes in-range-choice)]
                (should= correct-game-mode
                         (with-in-str (str out-range-choice "\n" in-range-choice) 
                           (get-mode delay-time))))))
