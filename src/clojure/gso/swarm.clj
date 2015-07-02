(ns gso.swarm
  (:require [gso.glowworm :as glowworm]
            [gso.neighbor-selection :as ng-select]))

(defn make-update-luciferin-fn [objective-fn]
  (partial map #(assoc % :luciferin (glowworm/luciferin % objective-fn))))

(defn make-next-swarm-fn [get-neighbors-of rand-fn]
  (fn [swarm]
    (map #(glowworm/create-next-glowworm
           get-neighbors-of
           (ng-select/make-neighbor-selection-fn rand-fn)
           %
           swarm)
         swarm)))