(ns blottsbooks.atoms)

;; Create state using atom
(def counter (atom 0))

(defn greeting-msg [req]
  (swap! counter + 10) ;; use swap to update state
  (if (= @counter 100) ;; read state with @state
    (str "Congrats! You ar the " @counter " visitor!")
    (str "Welcome to Blotts Books!")))

(greeting-msg "request")

;; We can also wrap map with atoms
(def by-title (atom {}))

(defn add-book [{title :title :as book}]
  (swap! by-title #(assoc % title book)))

(defn del-book [title]
  (swap! by-title #(dissoc % title)))

(defn find-book [title]
  (get @by-title title))

(add-book {:title "1984" :copies 1948})
(add-book {:title "Harry Potter" :copies 100})

(find-book "1984")
(find-book "Harry Potter")

(del-book "1984")
(find-book "1984") ;=> nil

;; Atoms are easy to get lost when using more than one. 
;; Use ref to solve this, it's like transaction in database.
(def by-name (ref {}))
(def total-subscriber (ref 0))

(defn add-channel [{name :name :as channel}]
  (dosync ;; dosync guarantees that inner procedures are atomic
   (alter by-name #(assoc % name channel))
   (alter total-subscriber + (:subscribers channel))))
