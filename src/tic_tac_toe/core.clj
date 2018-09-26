(ns tic-tac-toe.core
  (:require [tic-tac-toe.board :refer [make-initial-board]]
            [tic-tac-toe.game :refer [run-game]]
            [tic-tac-toe.modes :refer [get-mode]]
            [tic-tac-toe.messages :refer [goodbye]]
            [tic-tac-toe.cli-ui :refer [clear-screen
                                        delay-in-secs
                                        goodbye-display
                                        replay?
                                        send-message]]))

(defn -main 
  []
  (let [delay-time 1]
    (loop [players (get-mode delay-time)]
      (run-game players (make-initial-board) delay-time)
      (let [replay-answer (replay? delay-time)]
        (if (= replay-answer "y")
          (recur (get-mode delay-time))
          (goodbye-display delay-time))))))
