(ns tic-tac-toe.unbeatable-comp (:require [tic-tac-toe.board :refer [get-possible-moves
                                                                     place-mark
                                                                     terminal-state?
                                                                     winner?]]
                                          [tic-tac-toe.marks :refer :all])) (def tie 0)
(def maximum-depth 10)

(defn- get-opp-marker
  [marker]
  (if (= player-one-mark marker) 
    player-two-mark 
    player-one-mark))

(defn- get-min-scoring-move
  [scores-to-moves]
  (last (apply min-key first scores-to-moves)))

(defn- get-max-scoring-move
  [scores-to-moves]
  (last (apply max-key first scores-to-moves)))

(defn- at-max-depth?
  [depth]
  (> -1 (- maximum-depth depth)))

(defn- score-terminal-board
  [board marker depth minimaxer]
  (let [winner (winner? board)]
    (cond
      (= winner minimaxer) (- maximum-depth depth)
      (nil? winner) tie
      :else (- depth maximum-depth))))

(declare get-tile-from-computer)

(defn- get-best-next-board-for-marker
  [board marker depth minimaxer]
  (let [opp-marker (get-opp-marker marker)]
    (place-mark board
                (get-tile-from-computer board opp-marker depth minimaxer)
                opp-marker)))

(defn- find-best-score
  [marker minimaxer current-best-results score]
  (let [best-score (:best-score current-best-results)]
    (if (= marker minimaxer) 
      (if (or (> score best-score) 
              (nil? best-score)) 
        score 
        best-score)
      (if (or (< score best-score) 
              (nil? best-score)) 
        score 
        best-score))))

(defn- find-best-move
  [marker minimaxer current-best-results score move]
  (let [best-score (:best-score current-best-results)
        best-move (:best-move current-best-results)]
    (if (= marker minimaxer) 
      (if (or (> score best-score) 
              (nil? best-score)) 
        move 
        best-move)
      (if (or (< score best-score) 
              (nil? best-score)) 
        move 
        best-move))))

(defn- find-alpha
  [marker minimaxer current-best-results score]
  (let [current-alpha (:alpha current-best-results)]
    (if (= marker minimaxer) 
      (if (> score current-alpha) 
        score 
        current-alpha)
      current-alpha)))

  (defn- find-beta
    [marker minimaxer current-best-results score]
    (let [current-beta (:beta current-best-results)]
      (if (not= marker minimaxer) 
        (if (< score current-beta) 
          score 
          current-beta) 
        current-beta)))

    (defn- generate-result-map
      [current-best-results board marker depth move minimaxer]
      (let [score (score-terminal-board board
                                        marker
                                        depth 
                                        minimaxer)
            best-score (find-best-score marker 
                                        minimaxer
                                        current-best-results
                                        score)
            best-move (find-best-move marker 
                                      minimaxer
                                      current-best-results
                                      score
                                      move)
            alpha (find-alpha marker 
                              minimaxer
                              current-best-results
                              score)
            beta (find-beta marker 
                            minimaxer
                            current-best-results
                            score)]
        {:best-score best-score
         :best-move best-move
         :alpha alpha
         :beta beta}))

    (defn- score-move
      [current-best-results board marker depth move minimaxer]
      (if (or (terminal-state? board) (at-max-depth? depth))
        (generate-result-map current-best-results
                             board
                             marker
                             depth
                             move
                             minimaxer)
        (recur current-best-results 
               (get-best-next-board-for-marker board marker (inc depth) minimaxer)
               (get-opp-marker marker)
               (inc depth)
               move
               minimaxer)))

    (defn- get-optimum-move
      [board possible-moves marker depth minimaxer]
      (reduce (fn [current-best-results next-possible-move] 
                (if (>= (:alpha current-best-results) (:beta current-best-results))
                  current-best-results
                  (score-move current-best-results
                              board
                              marker
                              depth
                              next-possible-move
                              minimaxer)))
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
