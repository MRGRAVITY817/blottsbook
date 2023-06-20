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
