(ns gso.core
  (:require [gso.configuration :as configuration]
            [gso.simulation-factory :as simulation-factory]))

(defn- save-result-in-file [result output-path]
  (spit output-path (pr-str result)))

(defn -main [& args]
  (let [[config-path result-path] args
        config (configuration/load-from-file config-path)
        simulation (simulation-factory/make-gso-simulation config)]
    (save-result-in-file (simulation) result-path)))