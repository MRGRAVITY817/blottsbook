(ns blottsbooks.core
  (:gen-class)
  (:require
   [clojure.spec.alpha :as s]))

(s/def :blottsbooks.core/book
  (s/keys
   :req-un
   [:blottsbooks.core/title :blottsbooks.core/author :blottsbooks.core/copies]))

(defn say-welcome [what]
  (println "Welcome to" what "!"))

(defn find-by-title
  "Search for a book by title, 
   where title is a string and books is a collection
   of book maps, each of which must have a :title entry"
  [title books]
  (some #(when (= (:title %) title) %) books))

(defn number-of-copies-of
  "Return the number of copies in inventory of the given title, 
   where title is a string and books is a collection of book 
   maps each of which must have a :title entry"
  [title books]
  (:copies (find-by-title title books)))

(defn -main []
  (say-welcome "Blotts Books")
  (say-welcome "Osan city"))
