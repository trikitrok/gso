(ns gso.glowworm
  (:require [gso.vector-functions :as vec-fns]))

(def movement-step-size 0.03)

(defn make [& {:keys [coords params luciferin vision-range]}]
  (merge {:coords coords :luciferin luciferin :vision-range vision-range} params))

(defn luciferin 
  [{:keys [coords luciferin rho gamma]} obj-fn]
  (+ (* (- 1.0  rho) luciferin)
     (* gamma (obj-fn coords))))

(defn move-towards [{coords1 :coords :as g1} {coords2 :coords}]
  (assoc g1 :coords (vec-fns/move-towards-by-dist coords1 coords2 movement-step-size)))

(defn compute-vision-range 
  [{:keys [vision-range beta maximum-neighbors maximum-vision-range]} num-neighbors]
  (min maximum-vision-range 
       (max 0.0 (+ vision-range (* beta (- maximum-neighbors num-neighbors))))))