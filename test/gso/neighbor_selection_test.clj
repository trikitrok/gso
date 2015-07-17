(ns gso.neighbor-selection-test
  (:use midje.sweet)
  (:require [gso.glowworm :as glowworm]
            [gso.neighbor-selection :refer [make-neighbor-selection-fn]]))

(facts
  "about neighbors selection"
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
        neighbors [g2 g3 g4]]

    (let [select-neighbor (make-neighbor-selection-fn (constantly 0.55))]
      (select-neighbor g1 neighbors) => g4)

    (let [select-neighbor (make-neighbor-selection-fn (constantly 0.1))]
      (select-neighbor g1 neighbors) => g2)

    (let [select-neighbor (make-neighbor-selection-fn (constantly 0.35))]
      (select-neighbor g1 neighbors) => g3)))