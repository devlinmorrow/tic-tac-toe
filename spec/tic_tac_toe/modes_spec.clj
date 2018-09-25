(ns tic-tac-toe.modes-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.modes :refer :all]))

(describe "select mode"
          (it "returns game mode (i.e. the types of players) on second attempt, first attempt being out of range"
              (let [out-range-choice 9
                    in-range-choice 1
                    correct-game-mode (game-modes in-range-choice)]
                (should= correct-game-mode
                         (with-in-str (str out-range-choice "\n" in-range-choice) 
                           (get-mode))))))

(describe "replay?"
          (it "returns a replay answer of 'n' on second attempt, first attempt being an invalid answer"
              (let [invalid-answer "noo"
                    valid-answer "n"]
                (should= valid-answer
                         (with-in-str (str invalid-answer "\n" valid-answer) 
                           (replay? 0))))))
