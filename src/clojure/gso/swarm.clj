(ns gso.swarm
  (:require [gso.glowworm :as glowworm]
            [gso.neighbor-selection :as ng-select]))

(defn- create-next-swarm [search-neighbors update-luciferin select-neighbor swarm]
  (->> swarm
       update-luciferin
       (map #(glowworm/create-next-glowworm search-neighbors select-neighbor % swarm))))

(defn make-next-swarm-fn [search-neighbors random-double-in-0-1! obj-fn]
  (let [update-luciferin (partial map (gso.glowworm/make-update-luciferin-fn obj-fn))
        select-neighbor (ng-select/make-neighbor-selection-fn random-double-in-0-1!)]
    (partial create-next-swarm search-neighbors update-luciferin select-neighbor)))