(ns gso.core
  (:require [gso.algorithm :as gso-algorithm]
            [gso.neighbors-search :as neighbors]
            [gso.random-generation :as rng-fns]
            [gso.objective-functions :as objective-fns]
            [gso.swarm-factory :as swarm-factory]))

;; Optimization problem data
(def params
  {:gamma                0.6
   :rho                  0.4
   :beta                 0.08
   :maximum-neighbors    5
   :maximum-vision-range 5.0})

(def initial-values
  {:vision-range 0.2
   :luciferin    5.0})

(def seed 4357)

(def rng (rng-fns/make-mersenne-twister-rng seed))

(def swarm-size 200)

(def num-generations 100)

(def box [{:min 0 :max 5}
          {:min 0 :max 5}])

(def objective-fn objective-fns/j1)

;; Optimization problem data
(def initial-swarm
  (swarm-factory/create-random-swarm
    swarm-size initial-values params rng box))

(def gso-algorithm
  (gso-algorithm/make
    neighbors/search-neighbors
    (partial rng-fns/double-in-0-1! rng)
    objective-fn))

(gso-algorithm num-generations initial-swarm)