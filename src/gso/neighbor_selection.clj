(ns gso.neighbor-selection)

(defn- compute-probabilities [neighbors {luciferin-glowworm :luciferin}]
  (let [luciferin-diffs (map #(- (:luciferin %) luciferin-glowworm) neighbors)
        sum-diffs (reduce + luciferin-diffs)]
    (map #(/ % sum-diffs) luciferin-diffs)))

(defn- compute-accumulated-probabilities [probabilities]
  (map #(reduce + %)
       (for [n (range 1 (inc (count probabilities)))]
         (take n probabilities))))

(defn- get-neighbor-index [probabilities rand-fn]
  (let [rnd (rand-fn)
        accumulated-probabilities (compute-accumulated-probabilities probabilities)]
    (count (take-while #(< % rnd) accumulated-probabilities))))

(defn select-one-towards-to-move [neighbors glowworm rand-fn]
  (let [probabilities (compute-probabilities neighbors glowworm)
        neighbor-index (get-neighbor-index probabilities rand-fn)]
    (nth neighbors neighbor-index)))