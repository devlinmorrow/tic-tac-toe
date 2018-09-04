(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :refer [run]]
            [tic-tac-toe.messages :refer [ask-for-mode]]
            [tic-tac-toe.ui :refer [send-message]]))

(def game-modes {1 [{:type :human :mark "X"} {:type :human :mark "O"}] 
                 2 [{:type :human :mark "X"} {:type :comp :mark "O"}]
                 3 [{:type :comp :mark "X"} {:type :comp :mark "O"}]})

(defn ask-mode []
    (send-message ask-for-mode)
    (read-string (read-line)))

(defn get-players []
  (loop [mode (ask-mode)]
      (if (game-modes mode) 
        (game-modes mode)
        (recur (ask-mode)))))

(defn -main 
  []
  (let [players (get-players)]
  (run (get players 0) (get players 1))))
