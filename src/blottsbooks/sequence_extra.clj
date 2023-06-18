(ns blottsbooks.sequence-extra
  (:require
   [clojure.java.io :as io]))

;; Reading files as sequence
(defn listed-author? [author]
  (with-open [r (io/reader "authors.txt")]
    ; line-seq turn contents into sequence
    (some (partial = author) (line-seq r))))

(listed-author? "JK Rowling") ;=> true
(listed-author? "Jung Rae Cho") ;=> true
(listed-author? "Hoon Wee") ;=> nil

;; Regular expressions
(def re #"Pride and Prejudice.*")
(def title "Pride and Prejudice and Zombies")
(if (re-matches re title)
  (println "We have a classic!"))
(re-seq #"\w+" title) ;=> ("Pride" "and" "Prejudice" "and" "Zombies")

;; Sum up the prices!
(require '[blottsbooks.sequence :refer [books]])

(defn total-price
  "Total up book price"
  [books]
  (loop [books books total 0]
    (if (empty? books)
      total
      (recur (next books)
             (+ total (:price (first books)))))))
(total-price books)
;; Using map is better!
(defn better-total-price [books]
  (apply + (map :price books)))
(better-total-price books)

