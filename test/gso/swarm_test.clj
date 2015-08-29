(ns gso.swarm-test
  (:use midje.sweet)
  (:require [gso.swarm :refer [make-next-swarm-creation-fn]]
            [gso.objective-functions :as objective-functions]
            [gso.glowworm :as glowworm]
            [gso.neighbors-search :refer [search-neighbors]]
            [gso.random-generation :refer [make-mersenne-twister-rng double-in-0-1!]]))

(facts
  "about a swarm"

  (fact
    "the luciferin of each of its glowworms can be updated"

    (let [a-swarm [(glowworm/make
                     :coords [0.0 0.0]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 3.0
                     :vision-range 1.0)
                   (glowworm/make
                     :coords [0.1 0.0]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 5.0
                     :vision-range 1.0)
                   (glowworm/make
                     :coords [0.0 0.1]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 4.0
                     :vision-range 1.0)
                   (glowworm/make
                     :coords [0.2 0.0]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 6.0
                     :vision-range 1.0)]

          update-luciferin (partial map (glowworm/make-update-luciferin-fn objective-functions/j1))]

      (update-luciferin
        a-swarm) => [(glowworm/make
                       :coords [0.0 0.0]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 2.3886071058743075
                       :vision-range 1.0)
                     (glowworm/make
                       :coords [0.1 0.0]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 3.3585261352818985
                       :vision-range 1.0)
                     (glowworm/make
                       :coords [0.0 0.1]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 2.8639707100495175
                       :vision-range 1.0)
                     (glowworm/make
                       :coords [0.2 0.0]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 3.775322669562844
                       :vision-range 1.0)]))

  (fact
    "is used to compute the next swarm"
    (let [a-swarm [(glowworm/make
                     :coords [0.0 0.0]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 2.3886071058743075
                     :vision-range 1.0)
                   (glowworm/make
                     :coords [0.1 0.0]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 3.3585261352818985
                     :vision-range 1.0)
                   (glowworm/make
                     :coords [0.0 0.1]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 2.8639707100495175
                     :vision-range 1.0)
                   (glowworm/make
                     :coords [0.2 0.0]
                     :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                     :luciferin 3.775322669562844
                     :vision-range 1.0)]

          next-swarm (make-next-swarm-creation-fn
                       search-neighbors
                       (partial double-in-0-1! (make-mersenne-twister-rng 1437))
                       objective-functions/j1)]

      (next-swarm
        a-swarm) => [(glowworm/make
                       :coords [0.0 0.03]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 2.021771369398892
                       :vision-range 1.16)

                     (glowworm/make
                       :coords [0.13 0.0]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 2.3736418164510376
                       :vision-range 1.32)

                     (glowworm/make
                       :coords [0.026832815729997475 0.08658359213500127]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 2.182353136079228
                       :vision-range 1.24)

                     (glowworm/make
                       :coords [0.2 0.0]
                       :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                       :luciferin 2.4405162713005506
                       :vision-range 1.4)])))