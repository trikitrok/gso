(ns gso.test-helpers)

(defn contains-all-key-value-pairs-in? [a-map glowworm]
  (every? #(= (% a-map) (% glowworm))
          (keys a-map)))

(defn in-range? [lower-limit upper-limit num]
  (and (>= num lower-limit) (< num upper-limit)))