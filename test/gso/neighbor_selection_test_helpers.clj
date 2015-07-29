(ns gso.neighbor-selection-test-helpers
  (:require [gso.neighbor-selection :refer [make-neighbor-selection-fn]]))

(defn neighbor-selection-using-as-random-number [random-number]
  (let [random-in-01-fn (constantly random-number)]
    (make-neighbor-selection-fn random-in-01-fn)))

(defn neighbor-selection-using-a-real-random-generator []
  (make-neighbor-selection-fn rand))
