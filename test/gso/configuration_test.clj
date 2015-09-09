(ns gso.configuration-test
  (:use midje.sweet)
  (:use gso.configuration))

(facts
  "about configuration"

  (fact
    "it's loaded from a file"
    (load-from-file
      "test/gso/simulation-config.txt") => {:params          {:gamma                0.6
                                                              :rho                  0.4
                                                              :beta                 0.08
                                                              :maximum-neighbors    5
                                                              :maximum-vision-range 5.0}
                                            :initial-values  {:vision-range 3.0
                                                              :luciferin    5.0}
                                            :random-seed     324324
                                            :swarm-size      55
                                            :num-generations 150
                                            :boxes           [{:min -3 :max 3}
                                                              {:min -3 :max 3}]
                                            :obj-fn          :j1}))
