(ns gso.algorithm
  (:require [gso.swarm :as swarm-fns]))

(defn- run
  [next-swarm num-generations initial-swarm]
  (take num-generations (iterate next-swarm initial-swarm)))

(defn make
  [neighbors-fn random-double-in-0-1! obj-fn]
  (partial run (swarm-fns/make-next-swarm-creation-fn neighbors-fn random-double-in-0-1! obj-fn)))