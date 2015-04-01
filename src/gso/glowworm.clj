(ns gso.glowworm)

(defn make [id coords params luciferin]
  (merge {:id id :coords coords :luciferin luciferin} params))

(defn luciferin 
  [{:keys [coords luciferin rho gamma]} obj-fn]
  (+ (* (- 1.0  rho) luciferin)
     (* gamma (obj-fn coords))))

(defn- neighbor? [g1 g2]
  (and (not= g1 g2)
       (< (:luciferin g1) (:luciferin g2))))

(defn neighbors-of [g glowworms]
  (filter #(neighbor? g %) glowworms))