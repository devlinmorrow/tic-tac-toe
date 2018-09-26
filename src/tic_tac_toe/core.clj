(ns tic-tac-toe.core
  (:require [tic-tac-toe.board :refer [make-initial-board]]
            [tic-tac-toe.game :refer [run-game]]
            [tic-tac-toe.modes :refer [get-mode
                                       replay?]]
            [tic-tac-toe.messages :refer [goodbye]]
            [tic-tac-toe.cli-ui :refer [clear-screen
                                    delay-in-secs
                                    send-message]]))

(defn -main 
  []
  (let [delay-time 1]
    (loop [players (get-mode)]
      (clear-screen)
      (run-game players (make-initial-board) delay-time)
      (let [replay-answer (replay? delay-time)]
        (clear-screen)
        (if (= replay-answer "y")
          (do
            (recur (get-mode)))
          (do
            (send-message goodbye)
            (delay-in-secs delay-time)))))))
