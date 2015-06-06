(ns gso.random-generation
  (:import (random_generation MersenneTwister)))

(defn make-mersenne-twister-rng [seed]
  (MersenneTwister. seed))

(defn double-in-0-1!
  "On each invocation, it returns a random floating-point value
  uniformly distributed in the range [0, 1).
  It mutates the rng argument."
  [rng]
  (.nextDouble rng true true))

(defn double-in-range!
  "On each invocation, it returns a random floating-point value
  uniformly distributed in the range [lower-limit, upper-limit).
  It mutates the rng argument"
  [rng lower-limit upper-limit]
  (+ lower-limit (* (- upper-limit lower-limit) (double-in-0-1! rng))))

(defn- int-under-n!
  "On each invocation, it returns an integer value between 0 and n-1.
  It mutates the rng argument"
  [rng n]
  (.nextInt rng n))

(defn select-random-element! [rng elements]
  "On each invocation, it randomly selects and returns an element from the elements sequence.
  It mutates the rng argument"
  (nth elements (int-under-n! rng (count elements))))
