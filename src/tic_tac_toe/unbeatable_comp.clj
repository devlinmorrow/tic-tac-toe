(ns tic-tac-toe.unbeatable-comp (:require [tic-tac-toe.board :refer [get-possible-moves
                                                                     place-mark
                                                                     terminal-state?
                                                                     winner?]]
                                          [tic-tac-toe.marks :refer :all])) 

(def tie 0)
(def maximum-depth 10)

(defn get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(defn get-min-scoring-move
  [scores-to-moves]
  (last (apply min-key first scores-to-moves)))

(defn get-max-scoring-move
  [scores-to-moves]
  (last (apply max-key first scores-to-moves)))

(defn at-max-depth?
  [depth]
  (>= depth maximum-depth))

(defn score-terminal-board
  [board depth maximiser]
  (let [winner (winner? board)]
    (cond
      (= winner maximiser) (- maximum-depth depth)
      (nil? winner) tie
      :else (- depth maximum-depth))))

(defn find-best-score
  [marker maximiser current-best score]
  (let [best-score (:best-score current-best)]
    (if (= marker maximiser) 
      (if (or (nil? best-score) 
              (> score best-score)) 
        score 
        best-score)
      (if (or (nil? best-score) 
              (< score best-score)) 
        score 
        best-score))))

(defn find-best-move
  [marker maximiser current-best score move]
  (let [best-score (:best-score current-best)
        best-move (:best-move current-best)]
    (if (= marker maximiser) 
      (if (or (nil? best-score)(> score best-score)) 
        move 
        best-move)
      (if (or (nil? best-score)
              (< score best-score)  ) 
        move 
        best-move))))

(defn find-alpha
  [marker maximiser current-best score alpha]
    (if (= marker maximiser) 
      (if (> score alpha) 
        score 
        alpha)
      alpha))

(defn find-beta
  [marker maximiser current-best score beta]
    (if (not= marker maximiser) 
      (if (< score beta) 
        score 
        beta) 
      beta))

(defn generate-best-choice
  [current-best new-score new-move board marker depth  maximiser alpha beta]
  (let [best-score (find-best-score marker 
                                    maximiser
                                    current-best
                                    new-score)
        best-move (find-best-move marker 
                                  maximiser
                                  current-best
                                  new-score
                                  new-move)
        alpha (find-alpha marker 
                          maximiser
                          current-best
                          new-score
                          alpha)
        beta (find-beta marker 
                        maximiser
                        current-best
                        new-score
                        beta)]
    {:best-score best-score
     :best-move best-move
     :alpha alpha
     :beta beta}))

(declare minimax)

(defn score-move
  [current-best board marker depth move maximiser alpha beta]
  (if (terminal-state? board)
    (let [score (score-terminal-board board
                                      depth 
                                      maximiser)]
      (generate-best-choice current-best
                            score
                            move
                            board
                            marker
                            depth
                            maximiser
                            alpha
                            beta))
    (let [score (:best-score (minimax board
                                      (get-opp-marker marker)
                                      (inc depth)
                                      maximiser
                                      alpha
                                      beta))]
      (generate-best-choice current-best
                            score
                            move
                            board
                            marker
                            depth
                            maximiser
                            alpha
                            beta))))

(defn minimax
  [board marker depth maximiser alpha beta]
  (reduce (fn [current-best next-possible-move] 
            (if (>= alpha beta)
              current-best
              (score-move current-best
                          (place-mark board next-possible-move marker)
                          marker
                          depth
                          next-possible-move
                          maximiser
                          alpha
                          beta)))
          {:best-score nil
           :best-move nil}
          (get-possible-moves board)))

(defn get-tile-from-computer
  [board marker depth maximiser]
  (let [optimum-move (minimax board
                              marker
                              depth
                              maximiser
                              -1000
                              1000)]
    (:best-move optimum-move)))
