(ns gso.glowworm-test
  (:use midje.sweet)
  (:use [gso.glowworm :as glowworm])
  (:require [gso.objective-functions :as obj-fns]))

(facts
  "about a glowworm"
  
  (fact 
    "it computes its luciferin using an objective function (J1 in this case)"
    (let [g (glowworm/make 
              [0.0 0.0] {:gamma 0.6 :rho 0.4} 5.0)]
      (luciferin g obj-fns/j1) => (+ (* 5.0 (- 1.0 0.4))
                                     (* 0.6 0.9810118431238463)))))