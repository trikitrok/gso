(ns gso.swarm
  (:require [gso.glowworm :refer [make-update-luciferin-fn create-next-glowworm]]
            [gso.neighbor-selection :refer [make-neighbor-selection-fn]]))

(defn- create-next-swarm
  [search-neighbors update-luciferin select-neighbor swarm]
  (let [swarm-with-new-luciferin (update-luciferin swarm)]
    (map #(create-next-glowworm search-neighbors select-neighbor % swarm-with-new-luciferin)
         swarm-with-new-luciferin)))

(defn make-next-swarm-creation-fn
  [search-neighbors random-double-in-0-1! obj-fn]
  (let [update-luciferin (partial map (make-update-luciferin-fn obj-fn))
        select-neighbor (make-neighbor-selection-fn random-double-in-0-1!)]
    (partial create-next-swarm search-neighbors update-luciferin select-neighbor)))