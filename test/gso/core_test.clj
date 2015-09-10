(ns gso.core-test
  (:use midje.sweet)
  (:require [clojure.java.shell :refer [sh]]))

(facts
  "about running gso simulation from command line"

  (let [expected-simulation-results-path "test/gso/expected-simulation-results.txt"
        simulation-results-path "test/gso/simulation-results.txt"
        exit-code (:exit (sh "lein" "run"
                             "test/gso/simulation-config.txt"
                             simulation-results-path))]
    exit-code => 1
    (slurp simulation-results-path) => (slurp expected-simulation-results-path)))




