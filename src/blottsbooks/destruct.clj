(ns blottsbooks.destruct)

(def artists [:monet :austen :charlieputh :jayz])

;; Destructure with vector - first/second item
(let [[painter novelist] artists]
  (println "The painter is:" painter)
  (println "and the novelist is:" novelist))

;; Ignore some of the leading elements 
(let [[_ _ singer rapper] artists]
  (println "The singer is:" singer)
  (println "The rapper is:" rapper))

(def pairs [[:monet :austen] [:beethoven :dickinson]])

;; Destructure nested sequence
(let [[[painter] [composer]] pairs]
  (println "The painter is" painter)
  (println "The composer is" composer))

(let [[[painter] [_ poet]] pairs]
  (println "The painter is" painter)
  (println "The poet is" poet))
