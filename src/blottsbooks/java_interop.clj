(ns blottsbooks.java-interop)

;; Create a new instance of java.io.File
(def authors (java.io.File. "authors.txt"))

(if (.exists authors)
  (println "Our authors file is there.")
  (println "Our authors file is missing."))

(if (.canRead authors)
  (println "We can read it!"))

(.setReadable authors true)

;; Access public field as `.-<field> <instance>` 
(def rect (java.awt.Rectangle. 0 0 10 20))

(println "Coord: (" (.-x rect) "," (.-y rect) ")")
(println "Width:" (.-width rect))
(println "Height:" (.-height rect))
