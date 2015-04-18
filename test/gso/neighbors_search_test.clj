(ns gso.neighbors-search-test
  (:use midje.sweet)
  (:use [gso.neighbors-search])
  (:use [gso.glowworm :as glowworm]))

(facts
  "about finding neighbors"
  (let [g1 (glowworm/make
             :coords [0.0 0.0]
             :params {:gamma 0.6 :rho 0.4}
             :luciferin 5.0
             :vision-range 1.0)
        g2 (glowworm/make
             :coords [0.5 0.5]
             :params {:gamma 0.6 :rho 0.4}
             :luciferin 6.0
             :vision-range 1.0)
        g3 (glowworm/make
             :coords [0.5 0.5]
             :params {:gamma 0.6 :rho 0.4}
             :luciferin 5.0
             :vision-range 1.0)
        g4 (glowworm/make
             :coords [1.0 1.0]
             :params {:gamma 0.6 :rho 0.4}
             :luciferin 6.0
             :vision-range 1.0)]
    (fact
      "the glowworm is not a neighbor of itself"
      (neighbors-of g1 [g1]) => (empty [])
      (neighbors-of g1 [g1 g2]) => (just (list g2)))

    (fact
      "a glowworm's neighbor has a greater luciferin value"
      (neighbors-of g1 [g3]) => (empty [])
      (neighbors-of g1 [g1 g2 g3]) => (just (list g2)))

    (fact
      "a glowworm's neighbor is within its vision range"
      (neighbors-of g1 [g4]) => (empty [])
      (neighbors-of g1 [g1 g2 g3 g4]) => (just (list g2)))))