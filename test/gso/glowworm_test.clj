(ns gso.glowworm-test
  (:use midje.sweet)
  (:require [gso.glowworm :as glowworm]
            [gso.objective-functions :as objective-functions]
            [gso.neighbors-search :refer [search-neighbors]]
            [gso.neighbor-selection :refer [make-neighbor-selection-fn]]
            [gso.random-generation :refer [double-in-0-1! make-mersenne-twister-rng]]))

(facts
  "about a glowworm"

  (fact
    "it computes its luciferin using an objective function (J1 in this case)"
    (let [g (glowworm/make
              :coords [0.0 0.0]
              :params {:gamma 0.6 :rho 0.4}
              :luciferin 5.0
              :vision-range 1.0)]
      (glowworm/luciferin g objective-functions/j1) => (+ (* 5.0 (- 1.0 0.4))
                                                          (* 0.6 0.9810118431238463))))

  (fact
    "it updates a glowworm's luciferin"
    (let [a-glowworm
          (glowworm/make
            :coords [4.998708752014479 4.736005383108298]
            :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0 :maximum-neighbors 5 :beta 0.08}
            :luciferin 5.0
            :vision-range 0.2)

          expected-glowworm
          (glowworm/make
            :coords [4.998708752014479 4.736005383108298]
            :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0
                     :maximum-neighbors 5 :beta 0.08}
            :luciferin 3.0
            :vision-range 0.2)

          update-luciferin (glowworm/make-update-luciferin-fn objective-functions/j1)]

      (update-luciferin a-glowworm) => expected-glowworm))

  (facts
    "about moving a glowworm towards another"
    (let [g1 (glowworm/make
               :coords [1.0 1.0]
               :params {:gamma 0.6 :rho 0.4}
               :luciferin 5.0
               :vision-range 1.0)
          g2 (glowworm/make
               :coords [2.0 2.0]
               :params {:gamma 0.6 :rho 0.4}
               :luciferin 6.0
               :vision-range 1.0)
          g3 (glowworm/make
               :coords [2.0 2.0]
               :params {:gamma 0.6 :rho 0.4}
               :luciferin 6.0
               :vision-range 1.0)
          expected-value-for-x-and-y 1.0212132034355965]

      (fact
        "when they are different"
        (glowworm/move-towards g1 g2) => (assoc g1 :coords [expected-value-for-x-and-y
                                                            expected-value-for-x-and-y]))

      (fact
        "when they are the same"
        (glowworm/move-towards g2 g3) => g2)))

  (facts
    "about updating its vision range"
    (glowworm/compute-vision-range
      (glowworm/make
        :coords [1.0 1.0]
        :params {:gamma 0.6 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 1.0}
        :luciferin 5.0
        :vision-range 2.0)
      2) => 1.0

    (glowworm/compute-vision-range
      (glowworm/make
        :coords [1.0 1.0]
        :params {:gamma 0.6 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 3.0}
        :luciferin 5.0
        :vision-range 3.0)
      23) => 1.5600000000000001

    (glowworm/compute-vision-range
      (glowworm/make
        :coords [0.0 0.0]
        :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
        :luciferin 3.0
        :vision-range 1.0)
      3) => 1.16)

  (fact
    "about creating the next glowworm"
    (let [g1 (glowworm/make
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

          glowworms [g1 g2 g3 g4]]

      (glowworm/create-next-glowworm
        search-neighbors
        (make-neighbor-selection-fn (partial double-in-0-1! (make-mersenne-twister-rng 1437)))
        g1
        glowworms) => (glowworm/make
                        :coords [0.0 0.03]
                        :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                        :luciferin 2.3886071058743075
                        :vision-range 1.16)

      (glowworm/create-next-glowworm
        search-neighbors
        (make-neighbor-selection-fn (partial double-in-0-1! (make-mersenne-twister-rng 1437)))
        g2
        glowworms) => (glowworm/make
                        :coords [0.13 0.0]
                        :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                        :luciferin 3.3585261352818985
                        :vision-range 1.32)

      (glowworm/create-next-glowworm
        search-neighbors
        (make-neighbor-selection-fn (partial double-in-0-1! (make-mersenne-twister-rng 1437)))
        g3
        glowworms) => (glowworm/make
                        :coords [0.026832815729997475 0.08658359213500127]
                        :params {:gamma 0.6 :rho 0.4 :beta 0.08 :maximum-neighbors 5 :maximum-vision-range 5.0}
                        :luciferin 2.8639707100495175
                        :vision-range 1.24)

      (glowworm/create-next-glowworm
        search-neighbors
        (make-neighbor-selection-fn (partial double-in-0-1! (make-mersenne-twister-rng 1437)))
        g4
        glowworms) => (assoc g4 :vision-range 1.4))))