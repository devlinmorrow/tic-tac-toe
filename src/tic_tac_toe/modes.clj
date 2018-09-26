(ns tic-tac-toe.modes
  (:require [tic-tac-toe.messages :refer [ask-mode
                                          invalid-mode]]
            [tic-tac-toe.cli-ui :refer [attempt-get-number
                                        clear-screen
                                        delay-in-secs
                                        get-valid-number-without-board
                                        invalid-data-prompt
                                        send-message]]))

(def game-modes {1 [{:type :human :mark "X"} {:type :human :mark "O"}] 
                 2 [{:type :human :mark "X"} {:type :comp :mark "O"}]
                 3 [{:type :comp :mark "X"} {:type :comp :mark "O"}]})

(defn get-mode
  [delay-time]
  (loop [mode-choice (get-valid-number-without-board ask-mode delay-time)]
    (if (game-modes mode-choice) 
      (game-modes mode-choice)
      (do 
        (invalid-data-prompt invalid-mode delay-time)
        (recur (get-valid-number-without-board ask-mode delay-time))))))
