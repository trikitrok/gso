(ns gso.numeric-test-helpers)

(defn in-range? [lower-limit upper-limit num]
  (and (>= num lower-limit) (< num upper-limit)))
