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
  [board depth minimaxer]
  (let [winner (winner? board)]
    (cond
      (= winner minimaxer) (- maximum-depth depth)
      (nil? winner) tie
      :else (- depth maximum-depth))))

(declare get-tile-from-computer)

(defn get-best-next-board-for-marker
  [board marker depth minimaxer]
  (let [opp-marker (get-opp-marker marker)]
    (place-mark board
                (get-tile-from-computer board opp-marker depth minimaxer)
                opp-marker)))

(defn find-best-score
  [marker minimaxer best-choice score]
  (let [best-score (:best-score best-choice)]
    (if (= marker minimaxer) 
      (if (or (nil? best-score) 
              (> score best-score)) 
        score 
        best-score)
      (if (or (nil? best-score) 
              (< score best-score)) 
        score 
        best-score))))

(defn find-best-move
  [marker minimaxer best-choice score move]
  (let [best-score (:best-score best-choice)
        best-move (:best-move best-choice)]
    (if (= marker minimaxer) 
      (if (or (nil? best-score)(> score best-score)) 
        move 
        best-move)
      (if (or (nil? best-score)
              (< score best-score)  ) 
        move 
        best-move))))

(defn find-alpha
  [marker minimaxer best-choice score]
  (let [current-alpha (:alpha best-choice)]
    (if (= marker minimaxer) 
      (if (> score current-alpha) 
        score 
        current-alpha)
      current-alpha)))

(defn find-beta
  [marker minimaxer best-choice score]
  (let [current-beta (:beta best-choice)]
    (if (not= marker minimaxer) 
      (if (< score current-beta) 
        score 
        current-beta) 
      current-beta)))

(defn generate-best-choice
  [best-choice board marker depth move minimaxer]
  (let [score (score-terminal-board board
                                    depth 
                                    minimaxer)
        best-score (find-best-score marker 
                                    minimaxer
                                    best-choice
                                    score)
        best-move (find-best-move marker 
                                  minimaxer
                                  best-choice
                                  score
                                  move)
        alpha (find-alpha marker 
                          minimaxer
                          best-choice
                          score)
        beta (find-beta marker 
                        minimaxer
                        best-choice
                        score)]
    (println (str "  best-score  " best-score "  best-move  " best-move "  alpha  " alpha "  beta  " beta))
    {:best-score best-score
     :best-move best-move
     :alpha alpha
     :beta beta}))

(defn score-move
  [best-choice board marker depth move minimaxer]
  (println best-choice board marker depth move minimaxer)
  (if (terminal-state? board)
    (generate-best-choice best-choice
                          board
                          marker
                          depth
                          move
                          minimaxer)
    (recur best-choice 
           (get-best-next-board-for-marker board marker (inc depth) minimaxer)
           marker
           (inc depth)
           move
           minimaxer)))

(defn get-optimum-move
  [board possible-moves marker depth minimaxer]
  (reduce (fn [best-choice next-possible-move] 
            (if (>= (:alpha best-choice) (:beta best-choice))
              (do
                (println (str "reduce same best choice "best-choice))
              best-choice)
              (let [scored-move (score-move best-choice
                          (place-mark board next-possible-move marker)
                          marker
                          depth
                          next-possible-move
                          minimaxer)]
                (do (println (str "reduce updated scored move  " scored-move))
                    scored-move))))
          {:best-score nil
           :best-move nil 
           :alpha -100
           :beta 100}
          possible-moves))

(defn get-tile-from-computer
  [board marker depth minimaxer]
  (let [optimum-move (get-optimum-move board
                                       (get-possible-moves board)
                                       marker
                                       depth
                                       minimaxer)]
    (:best-move optimum-move)))
