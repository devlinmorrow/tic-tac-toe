(ns tic-tac-toe.cli-ui
  (:require [tic-tac-toe.messages :refer [ask-for-tile-choice
                                          ask-replay
                                          draw-message
                                          goodbye
                                          invalid-mode
                                          picked-tile-message
                                          not-integer-message
                                          not-y-or-n
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

(defn display-message
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
  (display-message (winner-message (:mark player)))
  (newline))

(defn draw-display []
  (newline)
  (display-message draw-message)
  (newline))

(defn present-move 
  [board delay-time]
  (clear-screen)
  (newline)
  (display-message picked-tile-message)
  (newline)
  (display-message (format-board-cli board))
  (delay-in-secs delay-time))

(defn starting-display []
  (clear-screen)
  (display-message welcome-message))

(defn invalid-data-prompt 
  [invalid-data-reason delay-time]
  (display-message invalid-data-reason)
  (delay-in-secs delay-time)
  (clear-screen))

(defn get-valid-number-without-board 
  [request-message delay-time]
  (loop []
    (clear-screen)
    (newline)
    (display-message request-message)
    (let [picked-number (attempt-get-number)]
      (if (nil? picked-number)
        (do
          (invalid-data-prompt not-integer-message delay-time)
          (recur))
        picked-number))))

(defn get-valid-number-with-board 
  [request-message board delay-time]
  (loop []
    (clear-screen)
    (newline)
    (display-message (format-board-cli board))
    (newline)
    (display-message request-message)
    (let [picked-number (attempt-get-number)]
      (if (nil? picked-number)
        (do
          (invalid-data-prompt not-integer-message delay-time)
          (recur))
        picked-number))))

(defn get-replay-answer []
  (display-message ask-replay)
  (newline)
  (get-string-from-user))

(defn replay? 
  [delay-time]
  (loop [answer (get-replay-answer)]
    (if (or (= answer "y") (= answer "n")) 
      answer
      (do 
        (invalid-data-prompt not-y-or-n delay-time)
        (recur (get-replay-answer))))))

(defn goodbye-display 
  [delay-time]
  (display-message goodbye)
  (delay-in-secs delay-time))
