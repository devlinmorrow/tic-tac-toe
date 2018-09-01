(ns tic-tac-toe.board
  (:require [tic-tac-toe.marks :refer :all]))

(defn make-initial-board
  []
  (let [grid-size 3]
  (into [] (map str (range 1 (+ (* grid-size grid-size) 1))))))

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

(defn top-row-winner?
  [board]
  (let [top-row [(get board 0) (get board 1) (get board 2)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn mid-row-winner?
  [board]
  (let [top-row [(get board 3) (get board 4) (get board 5)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn bot-row-winner?
  [board]
  (let [top-row [(get board 6) (get board 7) (get board 8)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn row-winner?
  [board]
  (cond 
    (top-row-winner? board) (top-row-winner? board)
    (mid-row-winner? board) (mid-row-winner? board)
    :else (bot-row-winner? board)))

(defn lft-col-winner?
  [board]
  (let [top-row [(get board 0) (get board 3) (get board 6)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn mid-col-winner?
  [board]
  (let [top-row [(get board 1) (get board 4) (get board 7)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn rt-col-winner?
  [board]
  (let [top-row [(get board 2) (get board 5) (get board 8)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn col-winner?
  [board]
  (cond 
    (lft-col-winner? board) (lft-col-winner? board)
    (mid-col-winner? board) (mid-col-winner? board)
    :else (rt-col-winner? board)))

(defn toplft-diag-winner?
  [board]
  (let [top-row [(get board 0) (get board 4) (get board 8)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn botlft-diag-winner?
  [board]
  (let [top-row [(get board 6) (get board 4) (get board 2)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) top-row) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) top-row) player-two-mark
      :else false)))

(defn diags-winner?
  [board]
  (if (toplft-diag-winner? board)
    (toplft-diag-winner? board)
    (botlft-diag-winner? board)))

(defn winner?
  [board]
  (cond
    (row-winner? board) (row-winner? board)
    (col-winner? board) (col-winner? board)
    :else (diags-winner? board)))

