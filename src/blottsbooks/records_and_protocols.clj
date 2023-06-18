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

;; You can extend protocol method, from outside.
;; No need to touch original record code!
(defprotocol Marketable
  (make-slogan [this]))

(extend-protocol Marketable
  Employee
  (make-slogan [e] (str (:first-name e) " is the Best Employee"))
  FakeCharacter
  (make-slogan [fc] (str (:name fc) " is the greatest character"))
  SuperComputer
  (make-slogan [sc] (str "This computer was produced in " (:production-year sc))))

(make-slogan sam) ;=> "Sam Waller is the greatest character"

;; You can also extend protocol for non-record types 
(extend-protocol Marketable
  String
  (make-slogan [s] (str \" s \" " is a string!")))

(make-slogan "This") ;=> "\"This\" is a string!"

;; One-off implementation for protocol (when we don't want to create record)
;; It doesn't require you to implement the whole protocol
(def person-component
  (reify Person
    (full-name [this] (println "Name"))
    (description [this] (println "This is person"))))





