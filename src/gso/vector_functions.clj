(ns gso.vector-functions)

(defn square-dist [v1 v2]
  (reduce + (map (comp #(* % %) -) v1 v2)))