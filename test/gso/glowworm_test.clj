(ns gso.glowworm-test
  (:use midje.sweet)
  (:require [gso.glowworm :as glowworm])
  (:require [gso.objective-functions :as obj-fns])
  (:require [gso.neighbors-search :as ng-search])
  (:require [gso.neighbor-selection :as ng-select]))

(facts
  "about a glowworm"

  (fact
    "it computes its luciferin using an objective function (J1 in this case)"
    (let [g (glowworm/make
              :coords [0.0 0.0]
              :params {:gamma 0.6 :rho 0.4}
              :luciferin 5.0
              :vision-range 1.0)]
      (glowworm/luciferin g obj-fns/j1) => (+ (* 5.0 (- 1.0 0.4))
                                     (* 0.6 0.9810118431238463))))
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
          expected-val (+ 1.0 (* glowworm/movement-step-size (/ 1 (Math/sqrt 2))))]
      (glowworm/move-towards g1 g2) => (assoc g1 :coords [expected-val expected-val])))

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
        :params {:gamma 0.6 :rho 0.4 :beta 0.08
                 :maximum-neighbors 5 :maximum-vision-range 5.0}
        :luciferin 3.0
        :vision-range 1.0)
      3) => 1.16)

  (fact
    "about creating the next glowworm"
    (let [g1 (glowworm/make
               :coords [0.0 0.0]
               :params {:gamma 0.6 :rho 0.4 :beta 0.08
                        :maximum-neighbors 5 :maximum-vision-range 5.0}
               :luciferin 3.0
               :vision-range 1.0)
          g2 (glowworm/make
               :coords [0.1 0.0]
               :params {:gamma 0.6 :rho 0.4 :beta 0.08
                        :maximum-neighbors 5 :maximum-vision-range 5.0}
               :luciferin 5.0
               :vision-range 1.0)
          g3 (glowworm/make
               :coords [0.0 0.1]
               :params {:gamma 0.6 :rho 0.4 :beta 0.08
                        :maximum-neighbors 5 :maximum-vision-range 5.0}
               :luciferin 4.0
               :vision-range 1.0)
          g4 (glowworm/make
               :coords [0.2 0.0]
               :params {:gamma 0.6 :rho 0.4 :beta 0.08
                        :maximum-neighbors 5 :maximum-vision-range 5.0}
               :luciferin 6.0
               :vision-range 1.0)
          glowworms [g1 g2 g3 g4]]
      (glowworm/create-next-glowworm
        ng-search/neighbors-of
        (ng-select/make-neighbor-selection-fn (constantly 0.55))
        g1
        glowworms) => (glowworm/make
                        :coords [0.03 0.0]
                        :params {:gamma 0.6 :rho 0.4 :beta 0.08
                                 :maximum-neighbors 5 :maximum-vision-range 5.0}
                        :luciferin 3.0
                        :vision-range 1.16))))