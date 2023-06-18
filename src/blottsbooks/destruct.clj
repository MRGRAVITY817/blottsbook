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

;; Not only vectors, but we can also destruct any seq-like data structures.
(let [[c1 c2 c3 c4] "Harry"]
  (println c1)
  (println c2)
  (println c3)
  (println c4))

;; We can also destruct function args
(defn artist-description [[novelist poet]]
  (str "The novelist is " novelist " and the poet is " poet))

(artist-description [:austen :dickinson])

;; We can destruct maps
(def artist-map {:painter :monet :novelist :austen})
(let [{painter :painter writer :novelist} artist-map]
  (println "The painter is" painter)
  (println "The writer is" writer))

;; Destructuring map is reversible
(let [{writer :novelist painter :painter} artist-map]
  (println "The writer is" writer)
  (println "The painter is" painter))

;; Destructuring nested map
(def austen {:name "Jane Austen"
             :parents {:father "George" :mother "Cassandra"}
             :dates {:born 1775 :died  1817}})
(let [{{dad :father mom :mother} :parents} austen]
  (println "Hello" mom "and" dad "!"))

;; Reducing redundancy
(def character-charlie {:name "Charlie Puth" :age 12 :gender :male})
(let [{:keys [name age gender]} character-charlie]
  (str "Name: " name  " Age: " age " Gender: " gender))

(defn add-gretting [character]
  (let [{:keys [name age]} character]
    (assoc character
           :greeting
           (str "Hello, my name is " name " and I am " age "."))))

(def greeted-charlie (add-gretting character-charlie))
greeted-charlie

;; Using `:as` to make above simple
(defn assoc-gretting [{:keys [name age] :as character}]
  (assoc character
         :greeting
         (str "Hello, my name is " name " and I am " age ".")))

(def hello-charlie (assoc-gretting character-charlie))
hello-charlie
;=> {:name "Charlie Puth",
;    :age 12,
;    :gender :male,
;    :greeting "Hello, my name is Charlie Puth and I am 12."}
