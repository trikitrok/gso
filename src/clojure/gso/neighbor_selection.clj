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

(defn- make-get-index-randomly
  [double-in-0-1!]
  (fn [accumulated-probabilities]
    (count (take-while #(< % (double-in-0-1!))
                  accumulated-probabilities))))

(defn- select-neighbor-index
  [get-index-randomly glowworm neighbors]
  (-> glowworm
      (selection-probabilities neighbors)
      accumulate-probabilities
      get-index-randomly))

(defn make-neighbor-selection-fn
  [double-in-0-1!]
  (let [get-index-randomly (make-get-index-randomly double-in-0-1!)]
    (fn [glowworm neighbors]
      (nth neighbors (select-neighbor-index get-index-randomly glowworm neighbors)))))

