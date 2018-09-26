(ns tic-tac-toe.cli-ui
  (:require [tic-tac-toe.messages :refer [ask-for-tile-choice
                                          draw-message
                                          picked-tile-message
                                          not-integer-message
                                          welcome-message
                                          winner-message]]))

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

(defn win-display
  [player]
  (newline)
  (send-message (winner-message (:mark player)))
  (newline))

(defn draw-display []
  (newline)
  (send-message draw-message)
  (newline))

(defn present-move 
  [board delay-time]
  (clear-screen)
  (newline)
  (send-message picked-tile-message)
  (newline)
  (send-message (format-board-cli board))
  (delay-in-secs delay-time))

(defn starting-display
  [board]
  (send-message welcome-message)
  (send-message (format-board-cli board)))
