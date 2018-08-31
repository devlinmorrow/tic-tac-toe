(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :refer [run]]))
            [tic-tac-toe.messages :refer :all]))

(defn ask-mode []
  (do
    (println ask-for-mode)
    (read-string (read-line))))

(defn get-players []
  (let [mode (ask-mode)]
    (cond
      (= 1 mode) [{:type :human :mark "X"} {:type :human :mark "O"}]
      (= 2 mode) [{:type :human :mark "X"} {:type :comp :mark "O"}]
      (= 3 mode) [{:type :comp :mark "X"} {:type :comp :mark "O"}])))

(defn -main 
  []
  (let [players get-players]
  (run (get players 0) (get players 1))))
