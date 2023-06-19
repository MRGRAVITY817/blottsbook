(ns blottsbooks.concurrency)

;; Use Thread. java class to create thread
(defn make-hoon-favorite [] (def fav-food "Gookbap"))
(defn make-eunbee-favorite [] (def fav-food "Pizza"))
(.start (Thread. make-hoon-favorite))
(.start (Thread. make-eunbee-favorite))
(println fav-food) ;; It might be Gookbap! (race condition)

;; Dynamic vars are safely separated by thread.
(def ^:dynamic *favorite-book* "Oliver Twist")

(def thread-1
  (Thread.
   #(binding [*favorite-book* "2001"]
      (println "My favorite book is" *favorite-book*))))
(def thread-2
  (Thread.
   #(binding [*favorite-book* "Emma"]
      (println "My favorite book is" *favorite-book*))))

(.start thread-1)
(.start thread-2)
