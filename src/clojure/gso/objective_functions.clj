(ns gso.objective-functions)

(defn j1 [[x y]]
  (- (* 3.0 (- 1 x) (- 1 x) (Math/exp (- (+ (* x x) (* (+ y 1) (+ y 1))))))
     (* 10.0 (- (/ x 5.0) (Math/pow x 3) (Math/pow y 5)) (Math/exp (- (+ (* x x) (* y y)))))
     (* (/ 1 3.0) (Math/exp (- (+ (* (+ x 1) (+ x 1)) (* y y)))))))

(defn j2 [[x y]]
  (+ 20.0 (+ (* x x)
             (* (- 10.0) (Math/cos (* 2 Math/PI x)))
             (* y y)
             (* (- 10.0) (Math/cos (* 2 Math/PI y))))))

(defn j3 [[x y]]
  (* (Math/pow (+ (* x x) (* y y)) 0.25)
     (+ 1.0 (Math/pow (Math/sin (* 50.0 (Math/pow (+ (* x x) (* y y)) 0.1))) 2.0))))
