(ns gso.random-generation
  (:import (random_generation MersenneTwister)))

(defprotocol RandomGeneration
  (double-in-range! [this lower-limit upper-limit]
    "On each invocation, it returns a random floating-point value
     uniformly distributed in the range [lower-limit, upper-limit).
     It mutates the rng field")

  (double-in-0-1! [this]
    "On each invocation, it returns a random floating-point value
    uniformly distributed in the range [0, 1).
    It mutates the rng field.")

  (select-random-element! [this elements]
    "On each invocation, it randomly selects and returns
    an element from the elements sequence.
    It mutates the rng field"))

(defrecord MersenneTwisterRandomGenerator [rng]
  RandomGeneration
  (double-in-range!
    [this lower-limit upper-limit]
    (+ lower-limit (* (- upper-limit lower-limit) (double-in-0-1! this))))

  (double-in-0-1!
    [this]
    (.nextDouble (:rng this) true true))

  (select-random-element!
    [this elements]
    (nth elements (.nextInt (:rng this) (count elements)))))

(defn make-mersenne-twister-rng [seed]
  (MersenneTwisterRandomGenerator. (MersenneTwister. seed)))
