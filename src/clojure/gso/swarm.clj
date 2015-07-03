(ns gso.swarm
  (:require [gso.glowworm :as glowworm]
            [gso.neighbor-selection :as ng-select]))

(defn- create-next-swarm [search-neighbors update-luciferin select-neighbor swarm]
  (->> swarm
       update-luciferin
       (map #(glowworm/create-next-glowworm search-neighbors select-neighbor % swarm))))

(defn make-next-swarm-fn [search-neighbors rand-fn obj-fn]
  (let [update-luciferin (partial map (gso.glowworm/make-update-luciferin-fn obj-fn))
        select-neighbor (ng-select/make-neighbor-selection-fn rand-fn)]
    (partial create-next-swarm search-neighbors update-luciferin select-neighbor)))