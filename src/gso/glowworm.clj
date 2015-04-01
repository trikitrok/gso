(ns gso.glowworm
  (:require [gso.vector-functions :as vec-fns]))

(defn make [id coords params luciferin]
  (merge {:id id :coords coords :luciferin luciferin} params))

(defn luciferin 
  [{:keys [coords luciferin rho gamma]} obj-fn]
  (+ (* (- 1.0  rho) luciferin)
     (* gamma (obj-fn coords))))

(defn- squared-dist [{coords1 :coords} {coords2 :coords}]
  (vec-fns/squared-dist coords1 coords2))

(defn- neighbor? [{:keys [vision-range] :as g1} g2]
  (and (not= g1 g2)
       (< (:luciferin g1) (:luciferin g2))
       (< (squared-dist g1 g2) (* vision-range vision-range))))

(defn neighbors-of [g glowworms]
  (filter #(neighbor? g %) glowworms))