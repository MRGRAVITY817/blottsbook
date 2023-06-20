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

;; Atoms and Refs will run again when there's conflict for updating value.
;; Hence putting side-effect logic in swap! or alter isn't a good solution.
;; Agent, creates it's own queue, and pushes processes with `send`
(def by-director (agent {}))

;; If agent process fails, restart agent
(if (agent-error by-director)
  (restart-agent
   by-director
   {}
   :clear-actions true))

(defn add-movie [{director :director :as movie}]
  (send
   by-director
   (fn [by-director-map]
     ;; even though the event is still in the queue,
     ;; it will immediately print this.
     ;; No redundant notifications anymore!
     (println "Added movie")
     (assoc by-director-map movie director))))

;; Shutdown all the agents 
(shutdown-agents)

;; TIPS
;; 1. Use var, when the value would be stable across local/thread
;; 2. Use ref, when we have numbers of mutable values that has to 
;;    be updated together.
;; 3. Use agent, when we need to put some side-effect
;; 4. Use atom, when it's side-effect-free and doesn't contain 
;;    any multiple values.
