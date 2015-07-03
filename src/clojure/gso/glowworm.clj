(ns gso.glowworm
  (:require [gso.vector-functions :as vec-fns]))

(def movement-step-size 0.03)

(defn make [& {:keys [coords params luciferin vision-range]}]
  (merge {:coords coords :luciferin luciferin :vision-range vision-range} params))

(defn luciferin
  [{:keys [coords luciferin rho gamma]} obj-fn]
  (+ (* (- 1.0 rho) luciferin)
     (* gamma (obj-fn coords))))

(defn make-update-luciferin-fn [obj-fn]
  (fn [glowworm] (assoc glowworm :luciferin (luciferin glowworm obj-fn))))

(defn move-towards
  [{original-coords :coords :as glowworm} {other-coords :coords}]
  (if (= original-coords other-coords)
    glowworm
    (assoc
      glowworm
      :coords
      (vec-fns/move-towards-by-dist original-coords other-coords movement-step-size))))

(defn compute-vision-range
  [{:keys [vision-range beta maximum-neighbors maximum-vision-range]} num-neighbors]
  (min maximum-vision-range
       (max 0.0 (+ vision-range (* beta (- maximum-neighbors num-neighbors))))))

(defn- update-vision-range [glowworm neighbors next-glowworm]
  (assoc next-glowworm :vision-range (compute-vision-range glowworm (count neighbors))))

(defn select-glowworm-to-move-to [select-neighbor glowworm neighbors]
  (if (empty? neighbors)
    glowworm
    (select-neighbor glowworm neighbors)))

(defn create-next-glowworm
  [search-neighbors select-neighbor glowworm glowworms]
  (let [neighbors (search-neighbors glowworm glowworms)]
    (->> neighbors
         (select-glowworm-to-move-to select-neighbor glowworm)
         (move-towards glowworm)
         (update-vision-range glowworm neighbors))))