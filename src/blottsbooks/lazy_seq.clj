(ns blottsbooks.lazy-seq)

;; Lazy sequence waits to be asked before it generates its elements.
;;
(def grettings "Hello world")
;; `repeat` creates unbounded sequence.
;; All unbounded sequence are lazy.
(def repeated-grettings (repeat grettings))
(first repeated-grettings)
(nth repeated-grettings 10)
(take 3 repeated-grettings)

;; Lets create books by choosing title/author using lazy sequence
(def numbers (iterate inc 1)) ;; Iterate will lazily perform inner procedure
(def titles (map #(str "Wheel of time, Book " %) numbers))

(def first-names ["Bob" "Jane" "Chuck" "Leo"])
(def last-names ["Jordan" "Austen" "Tolstoy" "Poe" "Dickens"])

(defn combine-names [fname lname]
  (str fname " " lname))

(def authors
  (map combine-names
       (cycle first-names)   ;; Cycle lazily around sequence
       (cycle last-names)))

(defn make-book [title author]
  {:author author :title title})
(def test-books (map make-book titles authors))

(nth test-books 3) ;=> {:author "Leo Poe", :title "Wheel of time, Book 4"}
