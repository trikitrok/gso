(ns gso.test-helpers)

(defn contains-all-key-value-pairs-in? [a-map glowworm]
  (every? #(= (% a-map) (% glowworm))
          (keys a-map)))

(defn in-range? [lower-limit upper-limit num]
  (and (>= num lower-limit) (< num upper-limit)))

(defn- nearly-equal? [actual expected tol]
  (every? #(> tol %)
          (map #(Math/abs (- %1 %2)) actual expected)))

(defn- matches [min-coords generation tol]
  (filter #(nearly-equal? min-coords (:coords %) tol)
          generation))

(defn- local-minimum-found? [min-matches tol generation min-coords]
  (<= min-matches (count (matches min-coords generation tol))))

(defn all-local-minima-found? [mins-coords min-matches tol generation]
  (every?
    (partial local-minimum-found? min-matches tol generation)
    mins-coords))

(defn coordinate-in-box? [{:keys [min max]} num]
  (in-range? min max num))

(defn each-glowworm-coordinate-in-its-box? [boxes {:keys [coords]}]
  (every? true? (map #(coordinate-in-box? %1 %2) boxes coords)))

(defn all-glowworms-in-their-box? [boxes glowworms]
  (every? true? (map (partial each-glowworm-coordinate-in-its-box? boxes) glowworms)))