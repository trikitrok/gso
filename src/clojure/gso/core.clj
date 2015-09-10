(ns gso.core
  (:require [gso.configuration :as configuration]
            [gso.simulation-factory :as simulation-factory]))

(defn- save-result-in-file [output-path result]
  (spit output-path (pr-str result)))

(defn- create-simulation-from [config-file]
  (-> config-file
      configuration/load-from-file
      simulation-factory/make-gso-simulation))

(defn -main [& args]
  (let [[config-path result-path] args
        simulation (create-simulation-from config-path)]
    (save-result-in-file (simulation) result-path)))
