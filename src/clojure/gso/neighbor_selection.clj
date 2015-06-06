(ns gso.neighbor-selection)

(defn- compute-probabilities-of-selecting [neighbors {luciferin-glowworm :luciferin}]
  (let [luciferin-diffs (map #(- (:luciferin %) luciferin-glowworm) neighbors)
        sum-diffs (reduce + luciferin-diffs)]
    (map #(/ % sum-diffs) luciferin-diffs)))

(defn- compute-accumulated-probabilities [probabilities]
  (map #(reduce + %)
       (for [n (range 1 (inc (count probabilities)))]
         (take n probabilities))))

(defn- get-index-randomly [accumulated-probabilities rnd]
  (count (take-while #(< % rnd) accumulated-probabilities)))

(defn select-neighbor [rand-fn glowworm neighbors]
  (nth neighbors
       (get-index-randomly
         (compute-accumulated-probabilities
           (compute-probabilities-of-selecting neighbors glowworm))
         (rand-fn))))