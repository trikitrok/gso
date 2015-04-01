(ns gso.vector-functions-test
  (:use midje.sweet)
  (:use [gso.vector-functions]))

(facts 
  "about functions on vectors"
  
  (facts 
    "about squared distance"
    (squared-dist [1.0 1.0] [1.0 1.0]) => 0.0
    (squared-dist [0.0 0.0] [1.0 1.0]) => 2.0
    (squared-dist [0.0 0.0 0.0] [1.0 1.0 1.0]) => 3.0
    (squared-dist [1.0 1.0] [1.0 2.0]) => 1.0)
  
  (facts 
    "about difference"
    (vec-diff [1 1] [1 1]) => [0 0]
    (vec-diff [1 1] [2 3]) => [-1 -2])
  
  (facts 
    "about norm"
    (norm [0 0]) => 0.0))