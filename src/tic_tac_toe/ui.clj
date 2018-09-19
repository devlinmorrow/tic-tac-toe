(ns tic-tac-toe.ui
  (:require [tic-tac-toe.messages :refer [ask-for-choice
                                          not-integer-message]]))

(defn format-board-cli
  [board-values]
  (let [board-size (int (Math/sqrt (count board-values)))]
    (->> board-values
         (into [])
         (map (fn [x] (str x " ")))
         (partition board-size)
         (interpose ["\n"])
         (apply concat) 
         clojure.string/join)))

(defn send-message
  [message]
  (println message))

(defn get-user-choice []
  (newline)
  (send-message ask-for-choice)
  (read-line))

(defn get-string-from-user []
  (read-line))

(defn get-number-from-user []
  (read-string (read-line)))

(defn get-user-tile-choice []
  (loop [tile-choice (get-user-choice)]
    (if (re-matches #"\d+" tile-choice)
      (read-string tile-choice)
      (do (send-message not-integer-message)
          (recur (get-user-choice))))))
