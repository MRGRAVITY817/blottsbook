(ns blottsbooks.spec
  (:require [clojure.spec.alpha :as s]))

;; Check if given value is valid spec

(s/valid? number? 44) ;=> true
(s/valid? number? :hello)

(def n-gt-10 (s/and number? #(> % 10)))
(s/valid? n-gt-10 1)
(s/valid? n-gt-10 10)
(s/valid? n-gt-10 11) ;=> true

(def n-or-s (s/or :a-number number? :a-string string?))

(s/valid? n-or-s "Hello!")
(s/valid? n-or-s 22)
(s/valid? n-or-s :hello) ;=> false, it's keyword

;; Spec'ing collections
(def coll-of-strings (s/coll-of string?))
(def coll-of-n-or-s (s/coll-of n-or-s))

(s/valid? coll-of-strings ["Alice" "in" "Wonderland"])
(s/valid? coll-of-n-or-s ["Alice" 3 "Wonderland" 4])

;; only n-element collections
(def s-n-n-s (s/cat :s1 string? :n1 number? :n2 number? :s2 string?))
(s/valid? s-n-n-s ["Harry" 1 2 "Potter"])

;; Spec'ing maps
(def title :title)
(def author :author)
(def copies :copies)

(def book-s
  (s/keys
   :req-un ; find 'un'qualified 'req'uirement
   [::title, ::author, ::copies]))

(s/valid? book-s {:title "Emma" :author "Austen" :copies 10})

;; Validating map with globally registered spec
(s/valid?
 :blottsbooks.core/book ; this one's defined in core.clj
 {:title "Dracula" :author "Stoker" :copies 19})

;; Conveniently defining spec 
(s/def ::movie (s/keys :req-un [::title ::director ::earning]))
(s/def ::title string?)
(s/def ::director string?)
(s/def ::earning int?)

(s/valid?
 ::movie
 {:title "Tenet" :director "Christopher Nolan" :earning 123})
;=> true

(s/valid?
 ::movie
 {:title "Tenet" :director "Christopher Nolan" :earning "123"})
;=> false

;; Get explanation for why value does/doesn't match
(s/explain
 ::movie
 {:title "Tenet" :director :christopher :earning "123"})

;; Return invalid/original data by match
(s/conform
 ::movie
 {:title "Tenet" :director :christopher :earning "123"})
;=> :clojure.spec.alpha/invalid
(s/conform
 ::movie
 {:title "Tenet" :director "Christopher Nolan" :earning 123})
;=> {:title "Tenet", :director "Christopher Nolan", :earning 123}
