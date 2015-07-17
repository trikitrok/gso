(ns gso.neighbors-search
  (:require [gso.vector-functions :as vector-functions]))

(defn- squared-dist [{coords1 :coords} {coords2 :coords}]
  (vector-functions/squared-dist coords1 coords2))

(defn- neighbor? [{:keys [vision-range] :as g1} g2]
  (and (not= g1 g2)
       (< (:luciferin g1) (:luciferin g2))
       (< (squared-dist g1 g2) (* vision-range vision-range))))

(defn search-neighbors [g glowworms]
  (filter #(neighbor? g %) glowworms))