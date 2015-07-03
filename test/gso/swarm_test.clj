(ns gso.swarm-test
  (:use midje.sweet)
  (:require [gso.swarm :as swarm-fns]
            [gso.objective-functions :as obj-fns]
            [gso.glowworm :as glowworm]
            [gso.neighbors-search :as ng-search]
            [gso.random-generation :as rng-fns]))

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

          update-luciferin (partial map (gso.glowworm/make-update-luciferin-fn obj-fns/j1))]

      (update-luciferin a-swarm) => [(glowworm/make
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

          next-swarm (swarm-fns/make-next-swarm-fn
                       ng-search/search-neighbors
                       (partial rng-fns/double-in-0-1! (rng-fns/make-mersenne-twister-rng 1437))
                       obj-fns/j1)]

      (next-swarm a-swarm) => [(glowworm/make
                                 :coords [0.03 0.0]
                                 :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                                 :luciferin 2.021771369398892
                                 :vision-range 1.08)

                               (glowworm/make
                                 :coords [0.13 0.0]
                                 :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                                 :luciferin 2.3736418164510376
                                 :vision-range 1.08)

                               (glowworm/make
                                 :coords [0.0 0.1]
                                 :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                                 :luciferin 2.182353136079228
                                 :vision-range 1.08)

                               (glowworm/make
                                 :coords [0.17316718427000255 0.013416407864998738]
                                 :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                                 :luciferin 2.4405162713005506
                                 :vision-range 1.16)])))