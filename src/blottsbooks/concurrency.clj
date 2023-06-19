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

;; Use .start to start thread process
(.start thread-1)
(.start thread-2)

;; Use .join to wait for thread process
(.join thread-1)

;; To return value from thread, create promise.
(def the-result (promise))
(deliver the-result "Emma")
;; Get promise value with `deref`
(println "The value in my promise is" (deref the-result))
;; Using @
(println "The value in my promise is" @the-result)

;; Run calculations in separate thread

(def inventory
  [{:title "Emma" :sold 51 :revenue 255}
   {:title "2001" :sold 17 :revenue 170}])

(defn sum-sold  [inv]
  (apply + (map :sold inv)))

(defn sum-revenue [inv]
  (apply + (map :revenue inv)))

(let [sold-promise (promise)
      revenue-promise (promise)]
  (.start (Thread. #(deliver sold-promise (sum-sold inventory))))
  (.start (Thread. #(deliver revenue-promise (sum-revenue inventory))))
  ;; main thread waits for these processes
  (println "Total number of books sold is" @sold-promise)
  (println "Total revenue is" @revenue-promise))

;; The process above is much easier with `future`
;; future creates promise and run on separate thread
(let [inv inventory
      sold-future (future (sum-sold inv))
      revenue-future (future (sum-revenue inv))]
  (println "Total number of books sold is" @sold-future)
  (println "Total revenue is" @revenue-future))

;; We can limit the number of threads, by creating thread pools
(import java.util.concurrent.Executors)

(def fixed-pool (Executors/newFixedThreadPool 3))

(defn a-lot-of-work []
  (println "Simulating function that takes a long time")
  (Thread/sleep 1000))

(defn even-more-work []
  (println "Simulating function that takes a long time.")
  (Thread/sleep 1000))

(.execute fixed-pool a-lot-of-work)
(.execute fixed-pool even-more-work)
(.execute fixed-pool even-more-work)
(.execute fixed-pool even-more-work)

;; Give a timeout when dereferencing long-taking future (in production)
(let [inv inventory
      sold-future (future (sum-sold inv))]
  (println "Book sold" (deref sold-future 500 :oh-snap)))
