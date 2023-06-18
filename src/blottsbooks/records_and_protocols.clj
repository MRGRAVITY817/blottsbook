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

;; Protocols are set _how to behave_ rules
;; It's like _traits_ in Rust
(defprotocol Person
  (full-name [this])
  (greeting [this msg])
  (description [this]))

(defrecord FakeCharacter [name appears-in author]
  Person
  (full-name [this] (:name this))
  (greeting [this msg] (str msg " " (:name this)))
  (description [this]
    (str (:name this) " is a character in " (:appears-in this))))

(defrecord Employee [first-name last-name department]
  Person
  (full-name [this] (str first-name " " last-name))
  (greeting [this msg] (str msg " " (:first-name this)))
  (description [this]
    (str (:first-name this) " works in " (:department this))))

(def sofia (->Employee "Sofia" "Diego" "Finance"))
(def sam (->FakeCharacter "Sam Waller" "The Pickwick Papers" "Dickens"))

(full-name sofia) ;=> "Sofia Diego"
(description sam) ;=> "Sam Waller is a character in The Pickwick Papers"





