(ns tic-tac-toe.ui
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
