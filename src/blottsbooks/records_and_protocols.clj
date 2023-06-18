(ns blottsbooks.records-and-protocols)

;; Maps are wonderful, but they are slow. 
;;   (because they accept everything)
;; Records are maps, but predefined with keys.
;;
;; Defining record 
(defrecord FictionalCharacter [name appears-in author])
;; Creating instance of record
(def watson (->FictionalCharacter "John Watson" "Sign of the Four" "Arthur Conan Doyle"))
(def elizabeth (map->FictionalCharacter
                {:name "Elizabeth Bennet"
                 :appears-in "Pride & Prejudice"
                 :author "Austen"}))

(:name elizabeth) ;=> "Elizabeth Bennet"
(:appears-in watson) ;=> "Sign of the Four";=> "Elizabeth Bennet"
(count watson) ;=> 3
(keys elizabeth) ;=> (:name :appears-in :author)

;; It's possible to add keyword like this,
;; but `:address` keyword will not get performance advantages.
(def address-watson (assoc watson :address "221B Baker Street"))

;; Check if the value is instance of record
(defrecord SuperComputer [name production-year based-on])
(instance? SuperComputer watson) ;=> false
(instance? FictionalCharacter watson) ;=> true
(class watson) ;=> blottsbooks.records_and_protocols.FictionalCharacter

;; instance and class are helpful, but don't abuse in real code.
;; BAD
(defn process-thing [x]
  (if (= (instance? FictionalCharacter x))
    (println "Fictional")
    (println "Computer")))




