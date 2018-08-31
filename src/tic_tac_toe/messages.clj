(ns tic-tac-toe.messages)

(def welcome-message "Welcome to Tic Tac Toe!")

(def ask-for-mode "Please enter 1 for human-vs-human, 2 for computer-vs-human or 
                  3 for computer-vs-computer.")

(def ask-for-choice "Please pick a tile number.")

(def already-picked-message "Uhoh, that one's taken!")

(def picked-tile-message "Here is the choice: ")

(def draw-message "It was a draw...")

(defn winner-message
  [player-mark]
  (str "Player " player-mark " won!"))
