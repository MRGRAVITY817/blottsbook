(ns blottsbooks.core-gen-test
  (:require
   [clojure.test.check.generators :as gen]
   [clojure.test.check.properties :as prop]
   [clojure.test.check.clojure-test :as ctest]
   [clojure.test.check :as tc]
   [blottsbooks.core :as b]))

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

;; creating prop test
(tc/quick-check ;; create 50 test
 50 (prop/for-all
     [shelf-and-book shelf-and-book-gen]
     (= (b/find-by-title (-> shelf-and-book :book :title) (:shelf shelf-and-book)) ;; actual
        (:book shelf-and-book)))) ;; expected

;; Smooth Integration (automatically use quick-check)
;; For all the bookshelf/book combinations,
;; we care to generate, looking for a book in the bookshelf 
;; with a given title should produce a book with that title.
(ctest/defspec find-by-title-find-books 50
  (prop/for-all
   [shelf-and-book shelf-and-book-gen]
   (= (b/find-by-title (-> shelf-and-book :book :title) (:shelf shelf-and-book))
      (:book shelf-and-book))))

(find-by-title-find-books)
