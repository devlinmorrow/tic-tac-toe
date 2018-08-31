(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]))

(describe "select mode"
          (it "asks user for game mode and returns h-vs-h when picked"
              (should= [{:type :human :mark "X"} {:type :human :mark "O"}]
                      (with-in-str "1" (get-players))))

          (it "asks user for game mode and returns h-vs-c when picked"
              (should= [{:type :human :mark "X"} {:type :comp :mark "O"}]
                      (with-in-str "2" (get-players))))

          (it "asks user for game mode and returns c-vs-c when picked"
              (should= [{:type :comp :mark "X"} {:type :comp :mark "O"}]
                      (with-in-str "3" (get-players))))) 
