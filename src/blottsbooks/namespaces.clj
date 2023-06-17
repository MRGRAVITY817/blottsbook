(ns blottsbooks.namespaces
  (:require [blottsbooks.symbols :as symbols])) ;; alias namespace with :as

;; Namespaces are like modules
;; It's a compilation of related features
;; Also, it's a look-up table for vars

;; you can get value from different namespace
(require 'clojure.data)
(def literature ["Emma" "Oliver Twist" "Possession"])
(def horror ["It" "Carry" "Possession"])
(clojure.data/diff literature horror)

;; It's possible to put require inside top `ns` form
(println symbols/PI)

;; Get specific value using :refer
(require '[symbols :refer [title]])
(println title)
;; The reason why we can just use `println`, not `clojure.core/println`
;; is because we referred all the features in `clojure.core` namespace
;; (require '[clojure.core :refer :all])

;; The current namespace name is bound to *ns*
(println "Current namespace" *ns*)
(find-ns 'user)
(find-ns 'fp)

;; Get all the defines in ns
(ns-map (find-ns 'user))
(ns-map 'user) ;; short form of above
(ns-map 'clojure.core)

;; Use keyword of current namespace
(def book {:title "Thinking Fast and Slow" ::author "Daniel Kanheman"})

;; Import Korma
(require '[korma.db :as db])

;; After fixing stuffs in imported namespace, we need to
;; re-import it after unmapping namespace feature.
(ns-unmap 'blottsbooks.symbols 'PI)
;; or you can auto reload the namespace after change
(require :reload '[blottsbooks.symbols :as symbols])
;; If there's some value that you don't want to auto-redefine, use `defonce`
(defonce heavy-process "Some process that takes gazillion years")


