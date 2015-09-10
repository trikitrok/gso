(ns gso.neighbor-selection-test
  (:use midje.sweet)
  (:require [gso.glowworm :as glowworm]
            [gso.test-helpers.neighbor-selection-helpers :as helpers]
            [gso.random-generation :refer [double-in-0-1! make-mersenne-twister-rng]]))

(facts
  "about neighbors selection"

  (fact
    "the very same glowworn is selected when it has no neighbor"
    (let [a-glowworn
          (glowworm/make
            :coords [0.0 0.0]
            :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
            :luciferin 3.0
            :vision-range 1.0)

          select-neighbor (helpers/neighbor-selection-using-a-real-random-generator)]

      (select-neighbor a-glowworn []) => a-glowworn))

  (fact
    "it ramdomly selects from neighbors with selection probabilities proportional to its luciferin"

    (let [a-glowworn
          (glowworm/make
            :coords [0.0 0.0]
            :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
            :luciferin 3.0
            :vision-range 1.0)

          neighbor1
          (glowworm/make
            :coords [0.1 0.0]
            :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
            :luciferin 5.0
            :vision-range 1.0)

          neighbor2
          (glowworm/make
            :coords [0.0 0.1]
            :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
            :luciferin 4.0
            :vision-range 1.0)

          neighrbor3
          (glowworm/make
            :coords [0.2 0.0]
            :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
            :luciferin 6.0
            :vision-range 1.0)

          neighbors [neighbor1 neighbor2 neighrbor3]]

      (let [select-neighbor (helpers/neighbor-selection-using-as-random-number 0.55)]
        (select-neighbor a-glowworn neighbors) => neighrbor3)

      (let [select-neighbor (helpers/neighbor-selection-using-as-random-number 0.1)]
        (select-neighbor a-glowworn neighbors) => neighbor1)

      (let [select-neighbor (helpers/neighbor-selection-using-as-random-number 0.35)]
        (select-neighbor a-glowworn neighbors) => neighbor2))))
