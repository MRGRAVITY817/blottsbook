(ns blottsbooks.macros)

(defn print-rating [rating]
  (cond
    (pos? rating)  (println "Good book!")
    (zero? rating) (println "Totally indifferent")
    :else          (println "Run away!")))

(defn arithmetic-if->cond [n pos zero neg]
  (list 'cond
        (list 'pos? n) pos
        (list 'zero n) zero
        :else neg))

(arithmetic-if->cond 'rating
                     '(println "Good book!")
                     '(println "Totally indifferent")
                     '(println "Run away!"))
;; (cond
;;   (pos? rating) (println "Good book!")
;;   (zero rating) (println "Totally indifferent")
;;   :else (println "Run away!"))

;; Better version, using macros
(defmacro arithmetic-if [n pos zero neg]
  (list 'cond
        (list 'pos? n) pos
        (list 'zero? n) zero
        :else neg))

(arithmetic-if rating :loved-it :meh :hated-it)

;; Using quotes
;; Quote gives us full-path for pos? zero? etc.
(def n 100)
(def pos "It's positive")
(def zero "It's zero!")
(def neg "It's negative!")

`(cond
   (pos? ~n) ~pos
   (zero? ~n) ~zero
   :else ~neg)

;;=> (clojure.core/cond
;;=>  (clojure.core/pos? 100)
;;=>  "It's positive"
;;=>  (clojure.core/zero? 100)
;;=>  "It's zero!"
;;=>  :else
;;=>  "It's negative!")

(defmacro arithmetic-if-quoted [n pos zero neg]
  `(cond
     (pos? ~n) ~pos
     (zero? ~n) ~zero
     :else ~neg))

;; Referring values within quotes
;; @ will expand body, peeling off the parens.
(defmacro our-defn [name args & body]
  `(def ~name (fn ~args ~@body)))
