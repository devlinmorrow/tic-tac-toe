(ns tic-tac-toe.cli-ui
  (:require [tic-tac-toe.messages :refer [ask-for-tile-choice
                                          not-integer-message]]))

(defn clear-screen []
  (print (str (char 27) "[2J")))

(defn delay-in-secs 
  [secs]
  (Thread/sleep (* secs 1000)))

(defn format-board-cli
  [board-values]
  (let [board-size (int (Math/sqrt (count board-values)))]
    (->> board-values
         (map (fn [x] (str x " ")))
         (partition board-size)
         (interpose "\n")
         (apply concat)
         clojure.string/join)))

(defn send-message
  [message]
  (println message))

(defn get-string-from-user []
  (read-line))

(defn attempt-get-number
  []
  (let [number-choice (read-line)]
    (if (re-matches #"\d+" number-choice)
      (read-string number-choice))))
