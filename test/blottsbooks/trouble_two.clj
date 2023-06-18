(ns blottsbooks.trouble-two
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :as ctest]
            [clojure.test.check.properties :as prop]))

(defn more-complex-f [a b]
  (let [denominator (- b 863947)]
    (if (zero? denominator)
      :no-result
      (/ a denominator))))

;; Unit test first
(deftest test-critical-value
  (testing "Singularity"
    (is (= :no-result (more-complex-f 1 863947)))))

(def non-critical-gen (gen/such-that (partial not= 863947) gen/pos-int))

;; Prop test
(ctest/defspec test-other-values 1000
  (prop/for-all [a gen/pos-int
                 b non-critical-gen]
                (= (* (more-complex-f a b) (- b 863947)) a)))
