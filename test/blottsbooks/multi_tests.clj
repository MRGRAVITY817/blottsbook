(ns blottsbooks.multi-tests
  (:require [clojure.test :refer [are deftest]]))

(deftest test-cons
  (are [x y] (= x y)
    (cons 1 nil) '(1)
    (cons nil nil) '(nil)

    (cons \a nil) '(\a)
    (cons \a "") '(\a)))

