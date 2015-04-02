(ns gso.vector-functions-test
  (:use midje.sweet)
  (:use [gso.vector-functions]))

(facts 
  "about functions on vectors"
  
  (facts 
    "about squared distance"
    (squared-dist [1.0 1.0] [1.0 1.0]) => 0.0
    (squared-dist [0.0 0.0] [1.0 1]) => 2.0
    (squared-dist [0.0 0.0 0.0] [1.0 1.0 1.0]) => 3.0
    (squared-dist [1.0 1.0] [1.0 2.0]) => 1.0)
  
  (facts 
    "about difference"
    (vec-diff [1 1] [1 1]) => [0 0]
    (vec-diff [1 1] [2 3]) => [-1 -2])
  
  (facts 
    "about norm"
    (norm [0 0]) => 0.0
    (norm [1 0]) => 1.0
    (norm [1 1]) => (Math/sqrt 2))
  
  (facts 
    "about computing a unit vector"
    (unit-vector [0 0] [1 0]) => [1.0 0.0]
    (unit-vector [0 0] [0 1]) => [0.0 1.0]
    (unit-vector [0 0] [1 1]) => [(/ 1.0 (Math/sqrt 2.0)) (/ 1.0 (Math/sqrt 2.0))]
    (unit-vector [0 0] [5 5]) => [(/ 1.0 (Math/sqrt 2.0)) (/ 1.0 (Math/sqrt 2.0))]
    
    (fact
      "it raises an error when origin and destination points are equal (norm 0 => division by 0)"
      (unit-vector [1 1] [1 1]) 
      => (throws IllegalArgumentException 
                 "Precondition failure on unit-vector, equal origin and destination: ([1 1] [1 1])")))
  
  (facts 
    "about moving a point in a direction"
    (move-point [0 0] [1 0] 5) => [5 0]
    (move-point [0 0] 
                [(/ 1.0 (Math/sqrt 2.0)) (/ 1.0 (Math/sqrt 2.0))] 
                (* 8.0 (Math/sqrt 2.0))) => [8.0 8.0]))