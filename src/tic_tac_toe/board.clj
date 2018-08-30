(ns tic-tac-toe.board)

(def initial-board [1 2 3 4 5 6 7 8 9])

(def grid-size 3)

(defn present-board
  [board-values]
  (print (clojure.string/join (apply concat (interpose ["\n"] (partition grid-size (map (fn [x] (str x " ")) (into [] board-values)))))) "\n"))

(defn place-mark
  [board position mark]
  (assoc board position mark))
