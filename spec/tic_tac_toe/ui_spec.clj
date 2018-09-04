(ns tic-tac-toe.ui-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board-spec :refer [empty-board]]
            [tic-tac-toe.ui :refer :all]))

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

(describe "get-user-tile-choice"
          (it "gets tile choice from UI and converts to number on second attempt, after first
              attempt is not an integer"
              (let [non-int-choice "3asv"
                    tile-choice "3"]
                (should= 3 
                         (with-in-str (str non-int-choice "\n" tile-choice) (get-user-tile-choice))))))
                        
