(ns tic-tac-toe.unbeatable-comp-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.marks :refer :all]
            [tic-tac-toe.unbeatable-comp :refer :all]))

(def starting-depth 0)

(describe "get-opp-marker"
          (it "gets player-one-mark when player two passed in"
              (should= player-one-mark
                       (get-opp-marker player-two-mark)))

          (it "gets player-two-mark when player one passed in"
              (should= player-two-mark
                       (get-opp-marker player-one-mark))))

(describe "get-min-scoring-move"
          (it "gets the move with the lowest score from a collection"
              (should= 2
                       (get-min-scoring-move '([10 0] [4 1] [-8 2])))))

(describe "get-max-scoring-move"
          (it "gets the move with the highest score from a collection"
              (should= 0
                       (get-max-scoring-move '([10 0] [4 1] [-8 2])))))

(describe "at-max-depth?"
          (it "is true if depth passed in is equal to or greater than to max"
              (should (at-max-depth? 10)))

          (it "is false if depth passed in is less than max"
              (should-not (at-max-depth? 9))))

(describe "score-terminal-board"
          (it "is 10 when winner is same as minimaxer and depth is 0"
              (should= 10
                       (score-terminal-board [player-two-mark player-two-mark player-two-mark
                                              "4" "5" "6"
                                              "7" player-one-mark player-one-mark]
                                             0
                                             player-two-mark)))

          (it "is 5 when winner is same as minimaxer and depth is 5"
              (should= 5
                       (score-terminal-board [player-two-mark player-two-mark player-two-mark
                                              "4" "5" "6"
                                              "7" player-one-mark player-one-mark]
                                             5
                                             player-two-mark)))

          (it "is -5 when winner is not same as minimaxer and depth is 5"
              (should= -5
                       (score-terminal-board [player-two-mark player-two-mark player-two-mark
                                              "4" "5" "6"
                                              "7" player-one-mark player-one-mark]
                                             5
                                             player-one-mark))))

(describe "find-best-score"
          (it "returns higher current score rather than previous best score (while minimaxer)"
              (should= 8
                       (find-best-score player-two-mark
                                        player-two-mark
                                        {:best-score 7}
                                        8)))

          (it "returns current score if previous best score is nil (while minimaxer)"
              (should= 8
                       (find-best-score player-two-mark
                                        player-two-mark
                                        {:best-score nil}
                                        8)))

          (it "returns previous best score which is still higher than current score (while minimaxer)"
              (should= 7
                       (find-best-score player-two-mark
                                        player-two-mark
                                        {:best-score 7}
                                        5)))

          (it "returns lower current score (while opponent)"
              (should= 5
                       (find-best-score player-one-mark
                                        player-two-mark
                                        {:best-score 7}
                                        5)))

          (it "returns current score if previous best score is nil (while opponent)"
              (should= 8
                       (find-best-score player-one-mark
                                        player-two-mark
                                        {:best-score nil}
                                        8)))

          (it "returns previous best score which is still lower than current score (while opponent)"
              (should= 7
                       (find-best-score player-one-mark
                                        player-two-mark
                                        {:best-score 7}
                                        8))))


(describe "find-best-move"
          (it "returns current move (while minimaxer, current score higher than best score)"
              (should= 5
                       (find-best-move player-two-mark
                                       player-two-mark
                                       {:best-score 7 :best-move 3}
                                       8
                                       5)))

          (it "returns previous best move (while minimaxer, current score lower than best score)"
              (should= 3
                       (find-best-move player-two-mark
                                       player-two-mark
                                       {:best-score 9 :best-move 3}
                                       8
                                       5)))

          (it "returns current move (while minimaxer, previous best score nil)"
              (should= 5
                       (find-best-move player-two-mark
                                       player-two-mark
                                       {:best-score nil :best-move nil}
                                       5
                                       5)))

          (it "returns current move (while opponent, current score lower than best score)"
              (should= 5
                       (find-best-move player-one-mark
                                       player-two-mark
                                       {:best-score 7 :best-move 3}
                                       5
                                       5)))

          (it "returns previous best move (while opponent, current score higher than best score)"
              (should= 3
                       (find-best-move player-one-mark
                                       player-two-mark
                                       {:best-score 7 :best-move 3}
                                       8
                                       5)))

          (it "returns current move (while opponent, previous best score nil)"
              (should= 5
                       (find-best-move player-one-mark
                                       player-two-mark
                                       {:best-score nil :best-move nil}
                                       8
                                       5))))

(describe "find-alpha"
          (it "returns current alpha (while minimaxer, current alpha higher than best alpha)"
              (should= 8
                       (find-alpha player-two-mark
                                   player-two-mark
                                   {:alpha 5}
                                   8)))

          (it "returns previous best alpha (while minimaxer, current alpha lower than best alpha)"
              (should= 5
                       (find-alpha player-two-mark
                                   player-two-mark
                                   {:alpha 5}
                                   3)))

          (it "returns previous best alpha (while opponent)"
              (should= 5
                       (find-alpha player-one-mark
                                   player-two-mark
                                   {:alpha 5}
                                   7))))

(describe "find-beta"
          (it "returns current beta (while opponent, current beta lower than best beta)"
              (should= -8
                       (find-beta player-one-mark
                                  player-two-mark
                                  {:beta -5}
                                  -8)))

          (it "returns previous best beta (while opponent, current beta lower than best beta)"
              (should= -5
                       (find-beta player-one-mark
                                  player-two-mark
                                  {:beta -5}
                                  -3)))

          (it "returns previous best beta (while minimaxer)"
              (should= -5
                       (find-beta player-two-mark
                                  player-two-mark
                                  {:beta -5}
                                  -7))))

(describe "get-tile-unbeatable-computer"
          (it "gets best tile position - 1"

              ; 1 X O
              ; O O X
              ; X 8 O 
              ; optimum move: 9 (position 8)

              (let [board ["1" player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" player-two-mark]]
                (should= 0
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - 1"

              ; O X O
              ; O O X
              ; X 8 9 
              ; optimum move: 9 (position 8)

              (let [board [player-two-mark player-one-mark player-two-mark
                           player-two-mark player-two-mark player-one-mark
                           player-one-mark "8" "9"]]
                (should= 8
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - 2"

              ; O X O
              ; 4 5 X
              ; X O O 
              ; optimum move: 5 (position 4)

              (let [board [player-two-mark player-one-mark player-two-mark
                           "4" "5" player-one-mark
                           player-one-mark player-two-mark player-two-mark]]
                (should= 4
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - 3"

              ; O 2 X
              ; X 5 6
              ; X O O 
              ; optimum move: 5 (position 4)

              (let [board [player-two-mark "2" player-one-mark 
                           player-one-mark "5" "6"
                           player-one-mark player-two-mark player-two-mark]]
                (should= 4
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - 4"

              ; X X 3
              ; 4 X 6
              ; O 8 O 
              ; optimum move: 8 (position 7)

              (let [board [player-one-mark player-one-mark "3"
                           "4" player-one-mark "6"
                           player-two-mark "8" player-two-mark]]
                (should= 7
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - 5"

              ; X 2 O
              ; O 5 6
              ; O X X 
              ; optimum move: 5 (position 4)

              (let [board [player-one-mark "2" player-two-mark
                           player-two-mark "5" "6"
                           player-two-mark player-one-mark player-one-mark]]
                (should= 4
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - 6 - testing depth so that it loses in as many turns as possible"

              ; 1 X 3
              ; 4 5 X
              ; O O X 
              ; optimum move: 3 (position 2)

              (let [board ["1" player-one-mark "3"
                           "4" "5" player-one-mark
                           player-two-mark player-two-mark player-one-mark]]
                (should= 2
                         (get-tile-from-computer board player-two-mark starting-depth player-two-mark))))

          (it "gets best tile position - 7 - player one is minimaxer"

              ; O O X
              ; 4 X 6
              ; 7 8 9 
              ; optimum move: 7 (position 6)

              (let [board [player-two-mark player-two-mark player-one-mark
                           "4" player-one-mark "6"
                           "7" "8" "9"]]
                (should= 6
                         (get-tile-from-computer board player-one-mark starting-depth player-one-mark)))))
