(ns gso.swarm-factory
  (:require [gso.random-generation :refer [double-in-range!]]
            [gso.glowworm :as glowworm]))

(defn- make-coordinate [{:keys [min max]} rng]
  (double-in-range! rng min max))

(defn- make-coordinates [boxes rng]
  (map #(make-coordinate % rng) boxes))

(defn create-random-swarm
  [swarm-size
   {:keys [vision-range luciferin]}
   params
   rng
   boxes]
  (for [_ (range swarm-size)]
    (glowworm/make
      :coords (make-coordinates boxes rng)
      :params params
      :luciferin luciferin
      :vision-range vision-range)))
