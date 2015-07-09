(ns gso.algorithm-test
  (:use midje.sweet)
  (:require [gso.algorithm :as gso-algorithm]
            [gso.neighbors-search :as neighbors]
            [gso.random-generation :as rng-fns]
            [gso.objective-functions :as objective-fns]
            [gso.swarm-factory :as swarm-factory]
            [gso.test-helpers :as test-helpers]))

(facts
  "About GSO algorithm"

  (fact
    "it finds the local minima when using J1 objective function"
    (let
      [params
       {:gamma                0.6
        :rho                  0.4
        :beta                 0.08
        :maximum-neighbors    5
        :maximum-vision-range 5.0}

       initial-values
       {:vision-range 3.0
        :luciferin    5.0}

       seed 324324

       rng (rng-fns/make-mersenne-twister-rng seed)

       swarm-size 55

       num-generations 150

       box [{:min -3 :max 3}
            {:min -3 :max 3}]

       initial-swarm
       (swarm-factory/create-random-swarm
         swarm-size initial-values params rng box)

       gso-algorithm
       (gso-algorithm/make
         neighbors/search-neighbors
         (partial rng-fns/double-in-0-1! rng)
         objective-fns/j1)

       last-generation
       (last (gso-algorithm num-generations initial-swarm))

       expected-local-minima [[1.28, 0.0] [0.0, 1.58] [-0.46, -0.63]]]

      (test-helpers/all-local-minima-found?
        expected-local-minima 3 0.05 last-generation) => true))

  (fact
    "it finds the local minima when using J2 objective function"
    (let
      [params
       {:gamma                0.6
        :rho                  0.4
        :beta                 0.08
        :maximum-neighbors    5
        :maximum-vision-range 2.0}

       initial-values
       {:vision-range 2.0
        :luciferin    5.0}

       seed 324324

       rng (rng-fns/make-mersenne-twister-rng seed)

       swarm-size 70

       num-generations 150

       box [{:min -1 :max 1}
            {:min -1 :max 1}]

       initial-swarm
       (swarm-factory/create-random-swarm
         swarm-size initial-values params rng box)

       gso-algorithm
       (gso-algorithm/make
         neighbors/search-neighbors
         (partial rng-fns/double-in-0-1! rng)
         objective-fns/j2)

       last-generation
       (last (gso-algorithm num-generations initial-swarm))

       expected-local-minima [[-0.5, -0.5], [-0.5, 0.5], [0.5, -0.5], [0.5, 0.5]]]

      (test-helpers/all-local-minima-found?
        expected-local-minima 5 0.05 last-generation) => true)))