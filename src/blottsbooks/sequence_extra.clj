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


