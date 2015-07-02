(ns gso.swarm-test
  (:use midje.sweet)
  (:require [gso.swarm :as swarm-fns]
            [gso.objective-functions :as obj-fns]
            [gso.glowworm :as glowworm]))

(facts
  "about a swarm"

  (fact
    "its luciferin can be updated"

    (let [a-swarm [(glowworm/make
                     :coords [4.998708752014479 4.736005383108298]
                     :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0 :maximum-neighbors 5 :beta 0.08}
                     :luciferin 5.0
                     :vision-range 0.2)
                   (glowworm/make
                     :coords [0.8428620901353456 1.1083384017043159]
                     :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0 :maximum-neighbors 5 :beta 0.08}
                     :luciferin 5.0
                     :vision-range 0.2)
                   (glowworm/make
                     :coords [2.4930515557995623 1.4826276240802505]
                     :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0 :maximum-neighbors 5 :beta 0.08}
                     :luciferin 5.0
                     :vision-range 0.2)]

          update-luciferin (swarm-fns/make-update-luciferin-fn obj-fns/j1)]

      (update-luciferin a-swarm) => [(glowworm/make
                                       :coords [4.998708752014479 4.736005383108298]
                                       :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0 :maximum-neighbors 5 :beta 0.08}
                                       :luciferin 3.0
                                       :vision-range 0.2)
                                     (glowworm/make
                                       :coords [0.8428620901353456 1.1083384017043159]
                                       :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0 :maximum-neighbors 5 :beta 0.08}
                                       :luciferin 4.8134092449192085
                                       :vision-range 0.2)
                                     (glowworm/make
                                       :coords [2.4930515557995623 1.4826276240802505]
                                       :params {:gamma 0.6 :rho 0.4 :maximum-vision-range 5.0 :maximum-neighbors 5 :beta 0.08}
                                       :luciferin 3.0295152166954975
                                       :vision-range 0.2)])))




