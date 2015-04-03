(ns gso.glowworm-test
  (:use midje.sweet)
  (:use [gso.glowworm :as glowworm])
  (:require [gso.objective-functions :as obj-fns]))

(facts
  "about a glowworm"
  
  (fact 
    "it computes its luciferin using an objective function (J1 in this case)"
    (let [g (glowworm/make 
              :id 0 
              :coords [0.0 0.0] 
              :params {:gamma 0.6 :rho 0.4} 
              :luciferin 5.0 
              :vision-range 1.0)]
      (luciferin g obj-fns/j1) => (+ (* 5.0 (- 1.0 0.4))
                                     (* 0.6 0.9810118431238463))))
  
  (facts 
    "about finding neighbors"
    (let [g1 (glowworm/make 
               :id 0 
               :coords [0.0 0.0] 
               :params {:gamma 0.6 :rho 0.4} 
               :luciferin 5.0 
               :vision-range 1.0)
          g2 (glowworm/make 
               :id 1 
               :coords [0.5 0.5] 
               :params {:gamma 0.6 :rho 0.4} 
               :luciferin 6.0 
               :vision-range 1.0)
          g3 (glowworm/make 
               :id 2 
               :coords [0.5 0.5] 
               :params {:gamma 0.6 :rho 0.4} 
               :luciferin 5.0 
               :vision-range 1.0)
          g4 (glowworm/make 
               :id 3 
               :coords [1.0 1.0] 
               :params {:gamma 0.6 :rho 0.4} 
               :luciferin 6.0 
               :vision-range 1.0)]
      (fact 
        "the glowworm is not a neighbor of itself"
        (neighbors-of g1 [g1]) => (empty [])
        (neighbors-of g1 [g1 g2]) => (just (list g2)))
      
      (fact 
        "a glowworm's neighbor has a greater luciferin value"
        (neighbors-of g1 [g3]) => (empty [])
        (neighbors-of g1 [g1 g2 g3]) => (just (list g2)))
      
      (fact 
        "a glowworm's neighbor is within its vision range"
        (neighbors-of g1 [g4]) => (empty [])
        (neighbors-of g1 [g1 g2 g3 g4]) => (just (list g2)))))
  
  (facts 
    "about moving a glowworm towards another"
    (let [g1 (glowworm/make 
               :id 0 
               :coords [1.0 1.0] 
               :params {:gamma 0.6 :rho 0.4} 
               :luciferin 5.0 
               :vision-range 1.0)
          g2 (glowworm/make 
               :id 1 
               :coords [2.0 2.0] 
               :params {:gamma 0.6 :rho 0.4} 
               :luciferin 6.0 
               :vision-range 1.0)
          expected-val (+ 1.0 (* movement-step-size (/ 1 (Math/sqrt 2))))]
      (move-towards g1 g2) => (assoc g1 :coords [expected-val expected-val])))
  
  (facts 
    "about updating its vision range"
    (compute-vision-range 
      (glowworm/make 
        :id 0 
        :coords [1.0 1.0] 
        :params {:gamma 0.6 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 1.0} 
        :luciferin 5.0 
        :vision-range 2.0) 
      2) => 1.0
    
    (compute-vision-range 
      (glowworm/make 
        :id 0 
        :coords [1.0 1.0] 
        :params {:gamma 0.6 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 3.0} 
        :luciferin 5.0 
        :vision-range 3.0) 
      23) => 1.5600000000000001))