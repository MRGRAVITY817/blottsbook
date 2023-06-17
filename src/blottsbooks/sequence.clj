(ns blottsbooks.sequence)

;; Sequence is a wrapper method for vector/list/map/set.
(def title-seq (seq ["Emma" "Harry" "Ron" "Robin"]))
(println title-seq)

(seq '("LOTR" "Harry Potter"))
(seq {:title "Harry Potter and the Philosopher's stone" :author "JK Rowling" :published-year 1994})
(seq (seq {:title "Harry Potter and the Philosopher's stone" :author "JK Rowling" :published-year 1994}))

;; (seq empty-sequence) = nil. 
;; This will be handy when finding falsiness.
(seq [])
(seq {})
(seq '())

(first (seq '("Emma" "Oliver Twist" "Robinson Crusoe")))
(rest (seq '("Emma" "Oliver Twist" "Robinson Crusoe")))

;; add new element at front
(cons "Emma" (seq ["Harry" "Ron"]))

;; let's count
(defn a-count
  ([input-seq] (a-count 0 input-seq))
  ([n input-seq]
   (if (seq input-seq)
     (a-count (inc n) (rest input-seq))
     n)))

;; This version is same as above, yet more optimized 
(defn my-count [input-seq]
  (let [the-seq (seq input-seq)]
    (loop [n 0 s the-seq] ;; n bound to 0, s bound to the-seq at start.
      (if (seq s)
        (recur (inc n) (rest s))
        n))))

(a-count '(1 2 3 4))
(my-count [1 2 3 4 5 6 7])

;; Handy sequence toolkits
(def titles ["James" "Emma" "2002" "Dracula"])
(sort titles) ;=> ("2002" "Dracula" "Emma" "James")
(reverse titles) ;=> ("Dracula" "2002" "Emma" "James")
(reverse (sort titles)) ;=> ("James" "Emma" "Dracula" "2002")

(def titles-and-authors ["Harry Potter" "JK Rowling" "Lord of the rings" "JRR Tolkein"])
(partition 2 titles-and-authors) ;=> (("Harry Potter" "JK Rowling") ("Lord of the rings" "JRR Tolkein"))

(def movies ["Jaws" "2012"])
(def directors ["Steven Spielberg" "Roland Emerich"])
(interleave movies directors) ;=> ("Jaws" "Steven Spielberg" "2012" "Roland Emerich")

(def scary-animals ["Lion" "Tigers" "Bears"])
(interpose "and" scary-animals) ;=> ("Lion" "and" "Tigers" "and" "Bears")

;; Higher order functions
(filter neg? [1 -22 3 -99 11]) ;=> (-22 -99)

(def books
  [{:title "Deep Six" :price 13.99 :genre :sci-fi :rating 6}
   {:title "Dracula" :price 1.99 :genre :horror :rating 7}
   {:title "Emma" :price 7.99 :genre :comedy :rating 9}
   {:title "2001" :price 10.50 :genre :sci-fi :rating 5}])
(defn cheap? [book]
  (when (<= (:price book) 9.99)
    book))
(filter cheap? books)
;=> ({:title "Dracula", :price 1.99, :genre :horror, :rating 7}
;=>  {:title "Emma", :price 7.99, :genre :comedy, :rating 9})
(some cheap? books)
;=> {:title "Dracula", :price 1.99, :genre :horror, :rating 7}
(if (some cheap? books)
  (println "We have cheap books for sale!"))

(def numbers [1 2 3 4])
(def doubled (map #(* 2 %) numbers))
doubled ;=> (2 4 6 8)

; These two are same
(map (fn [book] (:title book)) books)
(map :title books)
;=> ("Deep Six" "Dracula" "Emma" "2001")

; These three are same
(map (fn [book] (count (:title book))) books)
(map (comp count :title) books)
(for [b books]
  (count (:title b)))
;=> (8 7 4 4)

(defn add2 [a b]
  (+ a b))
(reduce add2 0 numbers)
(reduce + 0 numbers)
(reduce + numbers)
;=> 10
(defn hi-price [hi book]
  (if (> (:price book) hi)
    (:price book)
    hi))
(reduce hi-price 0 books) ;=> 13.99

;; Get top 3 high-rated books' title
(defn format-top-titles [books]
  (apply
   str
   (interpose
    " - "
    (map :title (take 3 (reverse (sort-by :rating books)))))))

;; Equivalent
(defn top-rated-titles [books]
  (->>
   books
   (sort-by :rating)
   reverse
   (take 3)
   (map :title)
   (interpose " - ")
   (apply str)))

(format-top-titles books)
(top-rated-titles books)
;=> "Emma - Dracula - Deep Six"


