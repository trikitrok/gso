(ns gso.random-generation-test
  (:use midje.sweet)
  (:require [gso.random-generation :refer :all]
            [gso.test-helpers.numeric-helpers :as helpers]))

(facts
  "about MersenneTwister generator"

  (fact
    "using the same seed equal random numbers sequences are generated"
    (let [seed 4357
          rng1 (make-mersenne-twister-rng seed)
          rng2 (make-mersenne-twister-rng seed)
          rand-sequence-1 (take 1000 (repeatedly #(double-in-0-1! rng1)))
          rand-sequence-2 (take 1000 (repeatedly #(double-in-0-1! rng2)))]
      rand-sequence-1 => rand-sequence-2))

  (fact
    "using different seeds different random numbers sequences are generated"
    (let [rng1 (make-mersenne-twister-rng 4357)
          rng2 (make-mersenne-twister-rng 4358)
          rand-sequence-1 (take 1000 (repeatedly #(double-in-0-1! rng1)))
          rand-sequence-2 (take 1000 (repeatedly #(double-in-0-1! rng2)))]
      rand-sequence-1 =not=> rand-sequence-2))

  (fact
    "double-in-0-1! produces numbers in range [0, 1)"
    (let [rng (make-mersenne-twister-rng 4357)
          in-range-0-1? (partial helpers/in-range? 0 1)
          rand-sequence (take 1000 (repeatedly #(double-in-0-1! rng)))]
      rand-sequence => (has every? in-range-0-1?)))

  (fact
    "double-in-range! produces numbers in range [lower-limit, upper-limit)"
    (let [rng (make-mersenne-twister-rng 4357)
          lower-limit 5
          upper-limit 10
          in-range? (partial helpers/in-range? lower-limit upper-limit)
          rand-sequence (take 1000 (repeatedly #(double-in-range! rng lower-limit upper-limit)))]
      rand-sequence => (has every? in-range?))))
