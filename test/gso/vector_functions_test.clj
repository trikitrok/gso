(ns gso.vector-functions-test
  (:use midje.sweet)
  (:use [gso.vector-functions]))

(facts 
  "about functions on vectors"
  
  (fact 
    "about squared distance"
    (squared-dist [1 1] [1 1]) => 0
    (squared-dist [0 0] [1 1]) => 2
    (squared-dist [0 0 0] [1 1 1]) => 3
    (squared-dist [1 1] [1 2]) => 1)
  
  (fact 
    "about difference"
    (vec-diff [1 1] [1 1]) => [0 0]
    (vec-diff [1 1] [2 3]) => [-1 -2]))