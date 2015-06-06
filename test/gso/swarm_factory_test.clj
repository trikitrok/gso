(ns gso.swarm-factory-test
  (:use midje.sweet)
  (:require [gso.swarm-factory :as swarm-factory]
            [gso.random-generation :as rng-fns]
            [gso.test-helpers :as test-helpers]))

(defn coordinate-in-box? [{:keys [min max]} num]
  (test-helpers/in-range? min max num))

(defn each-glowworm-coordinate-in-its-box? [boxes {:keys [coords]}]
  (every? true? (map #(coordinate-in-box? %1 %2) boxes coords)))

(defn all-glowworms-in-their-box? [boxes glowworms]
  (every? true? (map (partial each-glowworm-coordinate-in-its-box? boxes) glowworms)))

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
          rng (rng-fns/make-mersenne-twister-rng seed)
          initial-swarm (swarm-factory/create-random-swarm
                          swarm-size initial-values params rng boxes)]
      initial-swarm => (has every? #(test-helpers/contains-all-key-value-pairs-in? params %))
      initial-swarm => (has every? #(test-helpers/contains-all-key-value-pairs-in? initial-values %))
      (all-glowworms-in-their-box? boxes initial-swarm) => true?)))
