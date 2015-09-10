(ns gso.algorithm-test
  (:use midje.sweet)
  (:require [gso.simulation-factory :as simulation-factory]
            [gso.test-helpers.algorithm-helpers :as helpers]))

(facts
  "About GSO algorithm"

  (fact
    "it finds the local minima when using J1 objective function"
    (helpers/correct-simulation-results?
      (simulation-factory/make-gso-simulation
        {:params          {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
         :initial-values  {:vision-range 3.0 :luciferin 5.0}
         :random-seed     324324
         :swarm-size      55
         :num-generations 150
         :boxes           [{:min -3 :max 3} {:min -3 :max 3}]
         :obj-fn          :j1})
      :expected-local-minima [[1.28, 0.0] [0.0, 1.58] [-0.46, -0.63]]
      :min-matches 3
      :tol 0.05) => true)

  (fact
    "it finds the local minima when using J2 objective function"
    (helpers/correct-simulation-results?
      (simulation-factory/make-gso-simulation
        {:params          {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 2.0}
         :initial-values  {:vision-range 2.0 :luciferin 5.0}
         :random-seed     324324
         :swarm-size      70
         :num-generations 150
         :boxes           [{:min -1 :max 1} {:min -1 :max 1}]
         :obj-fn          :j2})
      :expected-local-minima [[-0.5, -0.5], [-0.5, 0.5], [0.5, -0.5], [0.5, 0.5]]
      :min-matches 5
      :tol 0.05)) => true)