(ns tic-tac-toe.cli-ui-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.cli-ui :refer :all]))

(def delay-time 0)

(describe "format-board-cli"
          (it "formats the board for CLI"
                (should= "1 2 3 \n4 5 6 \n7 8 9 "
                         (format-board-cli empty-board))))

(describe "attempt-get-number"
          (it "converts string of number to number"
                (should= 3 
                         (with-in-str "3" (attempt-get-number)))))
                        
          (it "returns nil if not number"
                (should= nil 
                         (with-in-str "3asv" (attempt-get-number))))

(describe "replay?"
          (it "returns a replay answer of 'n' on second attempt, first attempt being an invalid answer"
              (let [invalid-answer "noo"
                    valid-answer "n"]
                (should= valid-answer
                         (with-in-str (str invalid-answer "\n" valid-answer) 
                           (replay? delay-time))))))
