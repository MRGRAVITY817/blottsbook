(ns blottsbooks.core-gen-test
  (:require
   [clojure.test.check.generators :as gen]))

(def title-gen-unbound gen/string-alphanumeric)
(def copies-gen-unbound gen/pos-int)

;; We don't want empty string to be generated
(def title-gen (gen/such-that not-empty gen/string-alphanumeric))
(def author-gen (gen/such-that not-empty gen/string-alphanumeric))
(def copies-gen (gen/such-that (complement zero?) gen/pos-int))

;; We want to generater item-wise
(def book-gen
  (gen/hash-map
   :title title-gen :author author-gen :copies copies-gen))

;; Generate endless supply of books
(def bookshelf-gen (gen/not-empty (gen/vector book-gen)))
(take 2 bookshelf-gen)

;; Pluck out the book from collection
(def shelf-and-book-gen
  (gen/let [bookshelf bookshelf-gen
            book (gen/elements bookshelf)]
    {:shelf bookshelf :book book}))

(println :book shelf-and-book-gen)
