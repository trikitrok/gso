(ns gso.test-helpers.algorithm-helpers)

(defn- nearly-equal? [actual expected tol]
  (every? #(> tol %)
          (map #(Math/abs (- %1 %2)) actual expected)))

(defn- matches [min-coords generation tol]
  (filter #(nearly-equal? min-coords (:coords %) tol)
          generation))

(defn- local-minimum-found? [min-matches tol generation min-coords]
  (<= min-matches (count (matches min-coords generation tol))))

(defn- all-local-minima-found? [mins-coords min-matches tol generation]
  (every?
    (partial local-minimum-found? min-matches tol generation)
    mins-coords))

(defn correct-simulation-results?
  [run-gso-generations & {:keys [expected-local-minima min-matches tol]}]
  (let [last-generation (last (run-gso-generations))]
    (all-local-minima-found?
      expected-local-minima min-matches tol last-generation)))
