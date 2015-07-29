(ns gso.swarm-factory-test
  (:use midje.sweet)
  (:require [gso.swarm-factory :refer [create-random-swarm]]
            [gso.random-generation :refer [make-mersenne-twister-rng]]
            [gso.test-helpers.swarm-factory-helpers :as helpers]))

(facts
  "about creating swarms"

  (fact
    "a random swarm"
    (let [params {:gamma 0.6
                  :rho 0.4
                  :beta 0.08
                  :maximum-neighbors 5
                  :maximum-vision-range 5.0}
          initial-values {:vision-range 0.2
                          :luciferin 5.0}
          swarm-size 3
          boxes [{:min 0 :max 5}
               {:min 0 :max 5}]
          seed 4357
          rng (make-mersenne-twister-rng seed)
          initial-swarm (create-random-swarm
                          swarm-size initial-values params rng boxes)]
      initial-swarm => (has every? #(helpers/contains-all-key-value-pairs-in? params %))
      initial-swarm => (has every? #(helpers/contains-all-key-value-pairs-in? initial-values %))
      (helpers/all-glowworms-in-their-box? boxes initial-swarm) => true?)))
