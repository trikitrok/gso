(ns gso.objective-functions-test
  (:use midje.sweet)
  (:use [gso.objective-functions]))

(def precision (/ 1 1e8))

(facts 
  "about J1 function"
  (let 
    [coords (for [i (range 0 5) 
                  j (range 0 5)]
              [(- (* i 1.5) 3) (- (* j 1.5) 3)])
     
     expected-j1-values
     '(6.671280296717448E-5 -4.517936594085711E-4 -0.03650620461319554 -0.003078234252739988 3.223535961269275E-5 
       0.004156270134668844 0.3265409898164098 -2.773610019456342 0.4784411467828077 0.0311759440751708 
       -0.2449540405743497 -5.680276001896266 0.9810118431238463 7.996620241631349 0.2998710282262348 
       -0.02975999770667323 -0.4404918548935705 3.269463326439044 1.185275846660814 0.03200763718576478 
       -5.864187872589536E-6 0.003599520786098113 0.03312494992430833 0.0044245231361739 4.102972745826762E-5)]
    
    (map j1 coords) => (just (map #(roughly % precision) expected-j1-values))))

