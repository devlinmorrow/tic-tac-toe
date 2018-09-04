(ns tic-tac-toe.ui
  (:require [tic-tac-toe.messages :refer [ask-for-choice
                                          not-integer-message]]))

(defn format-board-cli
  [board-values]
  (clojure.string/join (apply concat (interpose ["\n"] (partition (int (Math/sqrt (count board-values))) (map (fn [x] (str x " ")) (into [] board-values)))))))

(defn send-message
  [message]
  (println message))

(defn get-user-choice []
  (newline)
  (send-message ask-for-choice)
  (read-line))


(defn get-user-tile-choice []
  (loop [tile-choice (get-user-choice)]
    (if (re-matches #"\d+" tile-choice)
      (read-string tile-choice)
      (do (send-message not-integer-message)
          (recur (get-user-choice))))))


