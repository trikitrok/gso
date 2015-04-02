(ns gso.vector-functions
  (:require [dire.core :refer [with-precondition! with-handler!]]))

(defn- square [a]
  (* a a))

(def ^:private squared-diff (comp square -))

(def ^:private sum (partial reduce +))

(defn- squared-norm [v]
  (sum (map square v)))

(defn vec-diff [v1 v2]
  (map - v1 v2))

(defn norm [v]
  (Math/sqrt (squared-norm v)))

(defn squared-dist [v1 v2]
  (sum (map squared-diff v1 v2)))

(defn unit-vector [v1 v2]
  (let [v1-v2 (vec-diff v2 v1)
        norm-v1-v2 (norm v1-v2)]
    (map #(/ % norm-v1-v2) v1-v2)))

(defn- multiply-by-scalar [v s]
  (map (partial * s) v))

(defn- sum-vec [v1 v2]
  (map + v1 v2))

(defn move-point [point direction dist]
  (sum-vec point (multiply-by-scalar direction dist)))

(defn move-towards-by-dist [p1 p2 dist]
  (move-point p1 (unit-vector p1 p2) dist))

;;
;; Preconditions and handlers
;;
(with-precondition! #'unit-vector
  "Precondition for unit-vector"
  :norm-0
  (fn [v1 v2]
    (not= v1 v2)))

(with-handler! #'unit-vector
  "Handler of unit-vector precondition norm-0"
  {:precondition :norm-0}
  (fn [e & args] 
    (throw (IllegalArgumentException. 
            (apply str 
                   "Precondition failure on unit-vector, equal origin and destination: " 
                   (vector args))))))