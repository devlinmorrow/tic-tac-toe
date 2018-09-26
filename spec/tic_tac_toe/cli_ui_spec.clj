(ns tic-tac-toe.cli-ui-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.cli-ui :refer :all]))

(def delay-time 0)

(describe "format-board-cli"
          (it "formats the board for CLI"
              (let [cli-formatted-board "1 2 3 \n4 5 6 \n7 8 9 "]
                (should= cli-formatted-board
                         (format-board-cli empty-board)))))

(describe "send-message"
          (it "sends message to output"
              (let [message "Hello, world!"]
                (should= (str message "\n")
                         (with-out-str (send-message message))))))

(describe "attempt-get-number"
          (it "returns number if number"
              (let [tile-choice "3"]
                (should= 3 
                         (with-in-str tile-choice (attempt-get-number)))))
                        
          (it "returns nil if not number"
              (let [non-int-choice "3asv"]
                (should= nil 
                         (with-in-str non-int-choice (attempt-get-number))))))

(describe "replay?"
          (it "returns a replay answer of 'n' on second attempt, first attempt being an invalid answer"
              (let [invalid-answer "noo"
                    valid-answer "n"]
                (should= valid-answer
                         (with-in-str (str invalid-answer "\n" valid-answer) 
                           (replay? delay-time))))))
