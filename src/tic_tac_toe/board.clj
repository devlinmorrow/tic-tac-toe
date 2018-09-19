(ns tic-tac-toe.board
  (:require [tic-tac-toe.marks :refer :all]))

(defn make-initial-board
  []
  (let [grid-size 3]
  (into [] (map str (range 1 (+ (* grid-size grid-size) 1))))))

(defn not-in-range?
  [board position]
  (if (get board position) false true))

(defn- eq-mark-one-or-two
  [mark]
  (or (= mark player-one-mark) (= mark player-two-mark)))

(defn tile-marked?
  [board position]
  (eq-mark-one-or-two (get board position)))

(defn place-mark
  [board position mark]
  (assoc board position mark))

(defn is-full?
  [board]
  (every? (fn [mark] (eq-mark-one-or-two mark)) board))

(defn- line-winning?
  [board pos-1 pos-2 pos-3]
  (let [line [(get board pos-1) (get board pos-2) (get board pos-3)]]
    (cond
      (every? (fn [mark] (= player-one-mark mark)) line) player-one-mark
      (every? (fn [mark] (= player-two-mark mark)) line) player-two-mark)))

(defn- top-row-winner?
  [board]
  (line-winning? board 0 1 2))

(defn- mid-row-winner?
  [board]
  (line-winning? board 3 4 5))

(defn- bot-row-winner?
  [board]
  (line-winning? board 6 7 8))

(defn- row-winner?
  [board]
  (cond 
    (top-row-winner? board) (top-row-winner? board)
    (mid-row-winner? board) (mid-row-winner? board)
    :else (bot-row-winner? board)))

(defn- lft-col-winner?
  [board]
  (line-winning? board 0 3 6))

(defn- mid-col-winner?
  [board]
  (line-winning? board 1 4 7))

(defn- rt-col-winner?
  [board]
  (line-winning? board 2 5 8))

(defn- col-winner?
  [board]
  (cond 
    (lft-col-winner? board) (lft-col-winner? board)
    (mid-col-winner? board) (mid-col-winner? board)
    :else (rt-col-winner? board)))

(defn- toplft-diag-winner?
  [board]
  (line-winning? board 0 4 8))

(defn- botlft-diag-winner?
  [board]
  (line-winning? board 6 4 2))

(defn- diags-winner?
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

(defn terminal-state?
  [board]
  (or (winner? board) (is-full? board)))
  
(defn get-indices-empty-tiles
  [board]
  (into [] (keep identity (flatten (map-indexed (fn [idx mark] [(if-not (eq-mark-one-or-two mark) idx)]) board)))))
