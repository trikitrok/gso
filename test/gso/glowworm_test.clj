(ns gso.glowworm-test
  (:use midje.sweet)
  (:use [gso.glowworm])
  (:require [gso.objective-functions]))

(facts
  "about a glowworm"
  
  (fact 
    "it computes it luciferin using an objective function (J1)"
    (let [g (gso.glowworm/make [0.0 0.0] {:gamma 0.6 :rho 0.4} 5.0)]
      (luciferin g gso.objective-functions/j1) => (+ (* 5.0 (- 1.0 0.4))
                                                     (* 0.6 0.9810118431238463)))))