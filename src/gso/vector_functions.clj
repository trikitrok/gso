(ns gso.vector-functions)

(defn- square [a]
  (* a a))

(defn vec-diff [v1 v2]
  (map - v1 v2))

(defn- squared-norm [v]
  (reduce + (map square v)))

(defn norm [v]
  (Math/sqrt (squared-norm v)))

(defn squared-dist [v1 v2]
  (squared-norm (vec-diff v1 v2)))