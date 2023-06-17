
;; Functions as values

(defn cheap? [book]
  (when (<= (:price book) 9.99)
    book))

(defn horror? [book]
  (when (= (:genre book) :horror)
    book))

(defn both? [first-predicate second-predicate book]
  (when (and (first-predicate book) (second-predicate book)) book))

(both? cheap? horror? {:price 10 :genre :horror})

;; Functions on the fly

((fn [n] (* 2 n)) 10)

(defn cheaper-f [max-price]
  (fn [book]
    (when (<= (:price book) max-price) book)))

(def real-cheap? (cheaper-f 1.00))
(def king-cheap? (cheaper-f 4.00))

(real-cheap? {:price 0.1 :genre :horror})
(king-cheap? {:price 11.1 :genre :horror})

(defn both-f [first-predicate second-predicate]
  (fn [book]
    (when (and (first-predicate book) (second-predicate book))
      book)))

(def cheap-horor? (both-f cheap? horror?))

(cheap-horor? {:price 1.1 :genre :horror})

(def cheap-horror-possesion?
  (both-f cheap-horor?
          (fn [book] (= (:title book) "Possesion"))))

(cheap-horror-possesion? {:price 1.1 :genre :horror :title "Posseson"})

;; Fucntional toolkit

(def addition +)
(def args [1 2 3])

(apply addition args)

(def names ["Harry" "Hermione" "Ron"])

(apply vector (apply list names))

(defn my-inc [n]
  (+ 1 n))

(def new-inc (partial + 1))

(new-inc 1)

(defn cheaper-than [max-price book]
  (when (<= (:price book) max-price)
    book))

(def very-cheap? (partial cheaper-than 1.00))
(def kinda-cheap? (partial cheaper-than 5.00))

(very-cheap? {:price 0.1})
(kinda-cheap? {:price 10.1})

(def not-very-cheap? (complement very-cheap?))

(not-very-cheap? {:price 1})

;; Function literals - use only for short functions
(#(+ %1 %2 %3) 1 2 3)
(#(* % 2) 3)

;; In the wild
(defn say-welcome [what]
  (println "Welcome to " what "!"))

;; Same as..
(def say-welcome-alt
  (fn [what] (println "Welcome to " what "!")))

(say-welcome "Mark")
(say-welcome-alt "Mark")

;; Handlers
(defn log-value
  "Log the message..."
  [msg value]
  (println msg value)
  value)

(defn wrap-logging
  "Return a fuction that logs the response (middleware) "
  [msg handler]
  (fn [request]
    (log-value msg (handler request))))

(defn wrap-content-type
  "Return a function that sets the response content type"
  [handler content-type]
  (fn [request]
    ;; Go spelunking through nested map levels with `assoc-in`
    (assoc-in
     (handler request)
     [:headers "Content-Type"]
     content-type)))

;; Using let for local values
(defn bad-get-discount [amount discount-percent min-charge]
  (if (> (* amount (- 1.0 discount-percent)) min-charge)
    (* amount (- 1.0 discount-percent))
    min-charge))

(defn get-discount [amount discount-percent min-charge]
  ;; Use `let [name value] ` to create local expression
  ;; Can bind multiple values
  (let [discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    ;; Can have multiple expressions in let body
    (println "Discount:" discount)
    (println "Discounted amount:" discounted-amount)
    (if (> discounted-amount min-charge)
      discounted-amount min-charge)))

(bad-get-discount 100 10 2)
(get-discount 100 0.75 60)

(def user-discounts {"Nico" 0.10 "Jonathan" 0.07 "Felix" 0.05})

;; Compute it in a let, use it in an fn
(defn mk-discount-price-f [user-name user-discounts min-charge]
  (let [discount-percent (user-discounts user-name)]
    (fn [amount]
      (let [discount (* amount discount-percent)
            discounted-amount (- amount discount)]
        (if (> discounted-amount min-charge)
          discounted-amount
          min-charge)))))

(def felix-price (mk-discount-price-f "Felix" user-discounts 10.0))

(felix-price 20.0)

;; conditional let
;;
(def anonymous-books {:title "Sir Gawain and the Green Knight"})
(def with-author {:title "Chronicles of Narnia" :author "Louis Carol"})

(defn uppercase-author-bad [book]
  (let [author (:author book)]
    (when author
      (.toUpperCase author))))

(defn uppercase-author [book]
  (if-let [author (:author book)]
    (.toUpperCase author)))

(uppercase-author-bad with-author)
(uppercase-author with-author)

(defn uppercase-author-when [book]
  (when-let [author (:author book)]
    (.toUpperCase author)))

(uppercase-author-when with-author)

;; let can be overridden
(defn get-title []
  (let [title "Pride and Prejudice"]
    (let [title "Sense and Sensibility"]
      (println title))))

(defn get-new-title []
  (let [title "Pride and prejudice"
        title (str title " and zombies")]
    (println title)))

(get-title)
(get-new-title)
