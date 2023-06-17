(ns blottsbooks.symbols)

;; `def` is for global and stable states
;; it lives as long as program end
;; ideal for defining constants
(def title "Emma")
(def PI 3.14)
(def COMPANY-NAME "Blotts Books")

;; `defn` is also like `def`, since it's just `def + fn`
(defn book-description [book]
  (str (:title book)
       " Written by "
       (:author book)))

;; Symbols are values, for real
'title

(str 'title)

;; bindings
(def author "Austen")
(def the-var #'author)

(.get the-var)
(.-sym the-var)

;; Dynamic vars with binding
(def ^:dynamic *debug-enabled* false)

(defn debug [msg]
  (when *debug-enabled* ;; earmuffs for bound var
    (println msg)))

(binding [*debug-enabled* true]
  (debug "Calling that darned function"))

;; There are predefined dynamic vars!
(def books ["Emma" "2002" "Harry Potter" "LOTR"])
(set! *print-length* 10) ;; show max 2 items from vector

(println books)

;; REPL binds last occurred exception in *e
(/ 1 0)
*e




