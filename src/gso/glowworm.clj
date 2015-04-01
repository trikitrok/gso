(ns gso.glowworm)

(defn make [coords params luciferin]
  (merge {:coords coords :luciferin luciferin} params))

(defn luciferin 
  [{:keys [coords luciferin rho gamma]} obj-fn]
  (+ (* (- 1.0  rho) luciferin)
     (* gamma (obj-fn coords))))