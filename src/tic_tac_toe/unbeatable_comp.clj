(ns tic-tac-toe.unbeatable-comp
  (:require [tic-tac-toe.board :refer [get-possible-moves
                                       place-mark
                                       terminal-state?
                                       winner?]]
            [tic-tac-toe.marks :refer :all]))

(def tie 0)
(def maximum-depth 10)

(defn maximising-depth?
  [depth]
  (= 0 (mod depth 2)))

(defn- get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(defn next-score-greater?
  [current-max next-scored-move]
  (or (nil? (first current-max)) 
      (> (first next-scored-move) 
         (first current-max))))

(defn next-score-lower?
  [current-min next-scored-move]
  (or (nil? (first current-min)) 
      (< (first next-scored-move) 
         (first current-min))))

(defn- get-min-scoring-move
  [idx-scores-map]
  (apply min-key first idx-scores-map))

(defn- get-max-scoring-move
  [idx-scores-map]
  (apply max-key first idx-scores-map))

(defn score-terminal-board
  [board depth maximiser]
  (let [winner (winner? board)]
    (cond
      (nil? winner) tie
      (= winner maximiser) (- maximum-depth depth)
      :else (- depth maximum-depth))))

(declare maximise)
(declare minimise)

(defn score-move
  [board marker depth move maximiser]
  (if (terminal-state? board)
    [(score-terminal-board board depth maximiser) move]
    (if (= marker maximiser)
      [(first (minimise board
                        (get-opp-marker marker)
                        (inc depth)
                        maximiser)) move]
      [(first (maximise board
                        (get-opp-marker marker)
                        (inc depth)
                        maximiser)) move])))

(defn minimise
  [board marker depth maximiser]
  (reduce (fn [current-min next-possible-move]
            (let [next-scored-move (score-move (place-mark board next-possible-move marker)
                                               marker
                                               depth
                                               next-possible-move
                                               maximiser)]
              (if (next-score-lower? current-min next-scored-move)
                next-scored-move
                current-min)))
          [nil nil]
          (get-possible-moves board)))

(defn maximise
  [board marker depth maximiser]
  (reduce (fn [current-max next-possible-move]
            (let [next-scored-move (score-move (place-mark board next-possible-move marker)
                                               marker
                                               depth
                                               next-possible-move
                                               maximiser)]
              (if (next-score-greater? current-max next-scored-move)
                next-scored-move
                current-max)))
          [nil nil]
          (get-possible-moves board)))

(defn get-tile-from-computer
  [board marker depth maximiser]
  (last (maximise board marker depth maximiser)))
