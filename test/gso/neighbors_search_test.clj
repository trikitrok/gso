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
      (search-neighbors g1 [g1]) => (empty [])
      (search-neighbors g1 [g1 g2]) => (just (list g2)))

    (fact
      "a glowworm's neighbor has a greater luciferin value"
      (search-neighbors g1 [g3]) => (empty [])
      (search-neighbors g1 [g1 g2 g3]) => (just (list g2)))

    (fact
      "a glowworm's neighbor is within its vision range"
      (search-neighbors g1 [g4]) => (empty [])
      (search-neighbors g1 [g1 g2 g3 g4]) => (just (list g2))))

  (let
    [g1 (glowworm/make
          :coords [0.0 0.0]
          :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
          :luciferin 2.3886071058743075
          :vision-range 1.0)
     g2 (glowworm/make
          :coords [0.1 0.0]
          :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
          :luciferin 3.3585261352818985
          :vision-range 1.0)
     g3 (glowworm/make
          :coords [0.0 0.1]
          :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
          :luciferin 2.8639707100495175
          :vision-range 1.0)
     g4 (glowworm/make
          :coords [0.2 0.0]
          :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
          :luciferin 3.775322669562844
          :vision-range 1.0)

     swarm [g1 g2 g3 g4]]

    (search-neighbors g1 swarm) => [g2 g3 g4]
    (search-neighbors g2 swarm) => [g4]
    (search-neighbors g3 swarm) => [g2 g4]
    (search-neighbors g4 swarm) => []))