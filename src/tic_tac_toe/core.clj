(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :refer [run]]
            [tic-tac-toe.modes :refer [get-mode
                                       replay?]]
            [tic-tac-toe.messages :refer [goodbye]]
            [tic-tac-toe.ui :refer [clear-screen
                                    delay-in-secs
                                    send-message]]))

(defn -main 
  []
  (let [delay-time 0]
    (loop [players (get-mode)]
      (clear-screen)
      (run (get players 0) (get players 1) delay-time)
      (let [replay-answer (replay? delay-time)]
        (clear-screen)
        (if (= replay-answer "y")
          (do
            (recur (get-mode)))
          (do
            (send-message goodbye)
            (delay-in-secs delay-time)))))))
