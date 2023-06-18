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

;; Lazy sequence, uses macro in itself
(defn chatty-vector []
  (println "Here we go!")
  [1 2 3])
(def s (lazy-seq (chatty-vector)))
;; Lazy sequence will only get evaluated when we access the items
(println s)

(defn my-repeat [x]
  (cons x (lazy-seq (my-repeat x))))

(nth (my-repeat "1") 10)

(defn my-iterate [f x]
  (cons x (lazy-seq (my-iterate f (f x)))))

(defn my-map [f col]
  (when-not (empty? col)
    (cons (f (first col))
          (lazy-seq (my-map f (rest col))))))

;; Read file using slurp
(def chap-numbers [1 2])

(defn chapters [numbers]
  (take 10
        (map slurp
             (map #(str "chap" % ".txt") numbers))))

(defn f-chapters [numbers]
  (->>
   numbers
   (map #(str "chap" % ".txt"))
   (map slurp)
   (take 2)))

(chapters chap-numbers)
(f-chapters chap-numbers)
;; `doall` will evaluate all the items in lazy-seq
(doall (f-chapters numbers))

;; DO NOT sort or reduce the unbounded sequence!
