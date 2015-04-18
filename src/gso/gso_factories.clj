(ns gso.gso-factories
  (:require [gso.glowworm :as glowworm])
  (:require [gso.neighbor-selection :as ng-select]))

(defn- create-update-luciferin-fn [objective-fn]
  (partial map #(glowworm/luciferin % objective-fn)))

(defn- create-gen-next-swarm-fn [get-neighbors-of rand-fn]
  (fn [swarm]
    (map #(glowworm/create-next-glowworm
           get-neighbors-of
           (partial ng-select/select-neighbor rand-fn)
           %
           swarm)
         swarm)))

(defn- create-gso-step-fn [get-neighbors-of rand-fn objective-fn]
  (let [update-luciferin (create-update-luciferin-fn objective-fn)
        gen-next-swarm (create-gen-next-swarm-fn get-neighbors-of rand-fn)]
    (comp gen-next-swarm update-luciferin)))

(defn create-gso-run-fn [get-neighbors-of rand-fn objective-fn]
  (let [next-swarm (create-gso-step-fn get-neighbors-of rand-fn objective-fn)]
    (fn [num-generations initial-swarm]
      (take num-generations (iterate next-swarm initial-swarm)))))