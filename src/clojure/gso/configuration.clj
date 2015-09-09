(ns gso.configuration)

(defn load-from-file [path]
  (read-string (slurp path)))
