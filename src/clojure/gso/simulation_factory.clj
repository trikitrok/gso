(ns gso.simulation-factory
  (:require [gso.algorithm :as gso-algorithm]
            [gso.neighbors-search :as neighbors]
            [gso.random-generation :as random-functions]
            [gso.objective-functions :as objective-functions]
            [gso.swarm-factory :as swarm-factory]))

(def obj-fns-by-key
  {:j1 objective-functions/j1
   :j2 objective-functions/j2
   :j3 objective-functions/j3})

(defn make-gso-simulation
  [{:keys [params initial-values random-seed swarm-size num-generations obj-fn boxes]}]
  (let [rng (random-functions/make-mersenne-twister-rng random-seed)

        obj-fn (obj-fns-by-key obj-fn)

        initial-swarm (swarm-factory/create-random-swarm
                        swarm-size initial-values params rng boxes)

        gso-algorithm (gso-algorithm/make
                        neighbors/search-neighbors
                        (partial random-functions/double-in-0-1! rng)
                        obj-fn)]

    #(gso-algorithm num-generations initial-swarm)))