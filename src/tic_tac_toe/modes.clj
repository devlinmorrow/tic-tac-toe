(ns tic-tac-toe.modes
  (:require [tic-tac-toe.game :refer [run]]
            [tic-tac-toe.messages :refer [ask-for-mode
                                          ask-replay
                                          goodbye
                                          not-y-or-n]]
            [tic-tac-toe.ui :refer [attempt-get-number
                                    clear-screen
                                    delay-in-secs
                                    get-string-from-user
                                    send-message]]))

(def game-modes {1 [{:type :human :mark "X"} {:type :human :mark "O"}] 
                 2 [{:type :human :mark "X"} {:type :comp :mark "O"}]
                 3 [{:type :comp :mark "X"} {:type :comp :mark "O"}]})

(defn- ask-mode []
  (send-message ask-for-mode)
  (attempt-get-number))

(defn get-mode []
  (loop [mode-choice (ask-mode)]
    (if (game-modes mode-choice) 
      (game-modes mode-choice)
      (do
        (clear-screen)
        (recur (ask-mode))))))

(defn- get-replay-answer []
  (send-message ask-replay)
  (newline)
  (get-string-from-user))

(defn replay? 
  [delay-time]
  (loop [answer (get-replay-answer)]
    (if (or (= answer "y") (= answer "n")) 
      answer
      (do 
        (send-message not-y-or-n)
        (newline)
        (delay-in-secs delay-time)
        (clear-screen)
        (recur (get-replay-answer))))))
