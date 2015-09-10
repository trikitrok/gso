(ns gso.test-helpers.swarm-factory-helpers
  (:require [gso.test-helpers.numeric-helpers :as numeric-test-helpers]))

(defn- coordinate-in-box? [{:keys [min max]} num]
  (numeric-test-helpers/in-range? min max num))

(defn- each-glowworm-coordinate-in-its-box? [boxes {:keys [coords]}]
  (every? true? (map #(coordinate-in-box? %1 %2) boxes coords)))

(defn all-glowworms-in-their-box? [boxes glowworms]
  (every? true? (map (partial each-glowworm-coordinate-in-its-box? boxes) glowworms)))

(defn contains-all-key-value-pairs-in? [a-map glowworm]
  (every? #(= (% a-map) (% glowworm))
          (keys a-map)))
