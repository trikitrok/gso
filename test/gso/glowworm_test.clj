(ns gso.glowworm-test
  (:use midje.sweet)
  (:use [gso.glowworm :as glowworm])
  (:require [gso.objective-functions :as obj-fns]))

(facts
  "about a glowworm"
  
  (fact 
    "it computes its luciferin using an objective function (J1 in this case)"
    (let [g (glowworm/make 0 [0.0 0.0] {:gamma 0.6 :rho 0.4} 5.0)]
      (luciferin g obj-fns/j1) => (+ (* 5.0 (- 1.0 0.4))
                                     (* 0.6 0.9810118431238463))))
  
  (facts 
    "about finding neighbors"
    (let [g1 (glowworm/make 0 [0.0 0.0] {:gamma 0.6 :rho 0.4} 5.0)
          g2 (glowworm/make 1 [0.5 0.5] {:gamma 0.6 :rho 0.4} 5.0)]
      (fact 
        "the glowworm is not a neighbor of itself"
        (neighbors-of g1 [g1]) => (empty [])
        (neighbors-of g1 [g1 g2]) => (just (list g2))))))

