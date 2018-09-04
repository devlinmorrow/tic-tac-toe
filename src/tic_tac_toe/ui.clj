(ns tic-tac-toe.ui
  (:require [tic-tac-toe.messages :refer [ask-for-choice]]))

(defn format-board-cli
  [board-values]
  (clojure.string/join (apply concat (interpose ["\n"] (partition (int (Math/sqrt (count board-values))) (map (fn [x] (str x " ")) (into [] board-values)))))))

(defn send-message
  [message]
  (println message))

(defn get-user-tile-choice []
  (newline)
  (send-message ask-for-choice)
  (read-string (read-line)))
