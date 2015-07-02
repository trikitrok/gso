(ns gso.algorithm
  (:require [gso.swarm :as swarm-fns]))

(defn make-step-fn [get-neighbors-of rand-fn objective-fn]
  (let [update-luciferin (swarm-fns/make-update-luciferin-fn objective-fn)
        gen-next-swarm (swarm-fns/make-next-swarm-fn get-neighbors-of rand-fn)]
    (comp gen-next-swarm update-luciferin)))

(defn make [get-neighbors-of rand-fn objective-fn]
  (let [next-swarm (make-step-fn get-neighbors-of rand-fn objective-fn)]
    (fn [num-generations initial-swarm]
      (take num-generations (iterate next-swarm initial-swarm)))))
