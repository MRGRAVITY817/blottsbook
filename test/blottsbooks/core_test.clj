(ns blottsbooks.core-test
  (:require [clojure.test :refer :all]
            [blottsbooks.core :as b]))

(def books
  [{:title "2001" :author "Clarke" :copies 21}
   {:title "Emma" :author "Austen" :copies 10}
   {:title "Misery" :author "King" :copies 101}])

;; You can run test in console with `lein test`
(deftest test-basic-inventory ;; Test suite

  (testing "Finding books"    ;; Test method
    (is (not (nil? (b/find-by-title "Emma" books))))
    (is (nil? (b/find-by-title "HARRY POTTER" books))))

  (testing "Copies in inventory"
    (is (= 10 (b/number-of-copies-of "Emma" books)))))

