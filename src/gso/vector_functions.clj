(ns gso.vector-functions)

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