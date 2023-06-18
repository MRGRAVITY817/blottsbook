(ns blottsbooks.destruct)

(def artists [:monet :austen])
;; Destructure with vector - first/second item
(let [[painter novelist] artists]
  (println "The painter is:" painter)
  (println "and the novelist is:" novelist))
