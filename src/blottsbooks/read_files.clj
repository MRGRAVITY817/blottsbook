(ns blottsbooks.read-files
  (:require
   [clojure.java.io :as io]))

(defn listed-author? [author]
  (with-open [r (io/reader "authors.txt")]
    ; line-seq turn contents into sequence
    (some (partial = author) (line-seq r))))

(listed-author? "JK Rowling") ;=> true
(listed-author? "Jung Rae Cho") ;=> true
(listed-author? "Hoon Wee") ;=> nil

