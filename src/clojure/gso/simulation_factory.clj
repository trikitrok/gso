(ns gso.simulation-factory
  (:require [gso.algorithm :as gso-algorithm]
            [gso.neighbors-search :as neighbors]
            [gso.random-generation :as rng-fns]
            [gso.objective-functions :as objective-fns]
            [gso.swarm-factory :as swarm-factory]))

(def obj-fns-by-key
  {:j1 objective-fns/j1
   :j2 objective-fns/j2
   :j3 objective-fns/j3})

(defn make-gso-simulation
  [{:keys [params initial-values random-seed swarm-size num-generations obj-fn boxes]}]
  (let [rng (rng-fns/make-mersenne-twister-rng random-seed)

        obj-fn (obj-fns-by-key obj-fn)

        initial-swarm (swarm-factory/create-random-swarm
                        swarm-size initial-values params rng boxes)

        gso-algorithm (gso-algorithm/make
                        neighbors/search-neighbors
                        (partial rng-fns/double-in-0-1! rng)
                        obj-fn)]

    #(gso-algorithm num-generations initial-swarm)))