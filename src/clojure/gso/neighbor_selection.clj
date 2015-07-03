(ns gso.neighbor-selection)

(defn- selection-probabilities
  [{luciferin-glowworm :luciferin} neighbors]
  (let [luciferin-diffs (map #(- (:luciferin %) luciferin-glowworm) neighbors)
        sum-diffs (reduce + luciferin-diffs)]
    (map #(/ % sum-diffs) luciferin-diffs)))

(defn- accumulate-probabilities
  [probabilities]
  (map #(reduce + %)
       (for [n (range 1 (inc (count probabilities)))]
         (take n probabilities))))

(defn- get-index-randomly
  [random-double-in-0-1! accumulated-probabilities]
  (count (take-while #(< % (random-double-in-0-1!))
                     accumulated-probabilities)))

(defn- select-neighbor-index
  [get-index-randomly glowworm neighbors]
  (-> glowworm
      (selection-probabilities neighbors)
      accumulate-probabilities
      get-index-randomly))

(defn- select-neighbor
  [get-index-randomly glowworm neighbors]
  (nth neighbors (select-neighbor-index get-index-randomly glowworm neighbors)))

(defn make-neighbor-selection-fn
  [random-double-in-0-1!]
  (partial select-neighbor (partial get-index-randomly random-double-in-0-1!)))

