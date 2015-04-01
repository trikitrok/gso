(ns gso.vector-functions)

(defn squared-dist [v1 v2]
  (reduce + (map (comp #(* % %) -) v1 v2)))

(defn vec-diff [v1 v2]
  (map - v1 v2))

(defn norm [v1]
  (Math/sqrt (squared-dist v1 v1)))