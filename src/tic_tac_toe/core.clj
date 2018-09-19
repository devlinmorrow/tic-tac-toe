(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :refer [run]]
            [tic-tac-toe.messages :refer [ask-for-mode
                                          ask-replay
                                          goodbye
                                          not-y-or-n]]
            [tic-tac-toe.ui :refer [get-number-from-user
                                    get-string-from-user
                                    send-message]]))

(def game-modes {1 [{:type :human :mark "X"} {:type :human :mark "O"}] 
                 2 [{:type :human :mark "X"} {:type :comp :mark "O"}]
                 3 [{:type :comp :mark "X"} {:type :comp :mark "O"}]})

(defn- ask-mode []
  (send-message ask-for-mode)
  (get-number-from-user))

(defn get-players []
  (loop [mode (ask-mode)]
    (if (game-modes mode) 
      (game-modes mode)
      (recur (ask-mode)))))

(defn- get-replay-answer []
  (send-message ask-replay)
  (get-string-from-user))

(defn- replay? []
  (loop [answer (get-replay-answer)]
    (if (or (= answer "y") (= answer "n")) 
      answer
      (do 
        (send-message not-y-or-n)
        (recur (get-replay-answer))))))

(defn -main 
  []
  (loop [players (get-players)]
    (run (get players 0) (get players 1))
    (if (= (replay?) "y")
      (recur (get-players))
      (send-message goodbye))))
