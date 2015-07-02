(ns gso.algorithm
  (:require [gso.swarm :as swarm-fns]))

(defn- run
  [next-swarm num-generations initial-swarm]
  (take num-generations (iterate next-swarm initial-swarm)))

(defn make
  [neighbors-fn rand-fn obj-fn]
  (partial run (swarm-fns/make-next-swarm-fn neighbors-fn rand-fn obj-fn)))


