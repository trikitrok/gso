(ns gso.objective-functions)

(defn- pow [x y] (Math/pow x y))

(defn- sin [x] (Math/sin x))

(defn- square [x] (* x x))

(defn- sum-squares [& values]
  (reduce + (map #(* % %) values)))

(defn- inverse-exp-of-squared-sum [x y]
  (Math/exp (- (sum-squares x y))))

(def ^:private *-2-pi (partial * 2 Math/PI))

(defn- j2-linear-term [x]
  (* (- 10.0) (Math/cos (*-2-pi x))))

(defn j1 [[x y]]
  (- (* 3 (square (dec x)) (inverse-exp-of-squared-sum x (inc y)))
     (* 10 (- (/ x 5.0) (pow x 3) (pow y 5)) (inverse-exp-of-squared-sum x y))
     (* 1/3 (inverse-exp-of-squared-sum (inc x) y))))

(defn j2 [[x y]]
  (+ 20.0
     (sum-squares x y)
     (j2-linear-term x)
     (j2-linear-term y)))

(defn j3 [[x y]]
  (let [squared-sum (sum-squares x y)]
    (* (pow squared-sum 1/4)
       (inc (square (sin (* 50.0 (pow squared-sum 1/10))))))))
