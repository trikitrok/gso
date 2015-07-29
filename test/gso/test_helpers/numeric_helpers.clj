(ns gso.test-helpers.numeric-helpers)

(defn in-range? [lower-limit upper-limit num]
  (and (>= num lower-limit) (< num upper-limit)))
