(ns tic-tac-toe.ui)

(defn format-board-cli
  [board-values]
  (clojure.string/join (apply concat (interpose ["\n"] (partition (int (Math/sqrt (count board-values))) (map (fn [x] (str x " ")) (into [] board-values)))))))

(defn send-message
  [message]
  (println message))
