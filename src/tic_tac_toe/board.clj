(ns tic-tac-toe.board
  (:require [tic-tac-toe.marks :refer :all]))

(defn make-initial-board
  [grid-size]
  (into [] (map str (range 1 (+ (* grid-size grid-size) 1)))))

(defn present-board
  [board-values]
  (print (clojure.string/join (apply concat (interpose ["\n"] (partition (int (Math/sqrt (count board-values))) (map (fn [x] (str x " ")) (into [] board-values)))))) "\n"))

(defn place-mark
  [board position mark]
  (assoc board position mark))

(defn eq-mark-one-or-two
  [mark]
  (or (= mark player-one-mark) (= mark player-two-mark)))

(defn is-full?
  [board]
  (every? (fn [mark] (eq-mark-one-or-two mark)) board))

