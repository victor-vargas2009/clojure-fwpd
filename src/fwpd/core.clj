; Turn the result of your glitter filter into a list of names.
; Write a function, append, which will append a new suspect to your list of suspects.
; Write a function, validate, which will check that :name and :glitter-index are present when you append. The validate function should accept two arguments: a map of keywords to validating functions, similar to conversions, and the record to be validated.
; Write a function that will take your list of maps and convert it back to a CSV string. You’ll need to use the clojure.string/join function.

(ns fwpd.core)

;; Deifnes file
(def filename "suspects.csv")

;; Defines the keys
(def vamp-keys [:name :glitter-index])

;; Converts a string into integer
(defn str->int
  [str]
  (Integer. str))

;; Conversion for map of vampires
;; this map is used to associate a conversion function to the value passed
;; in this case the glitter-index will be converted into an integer
;; and the name will passed as iss since the identity function just returns the value passed to it
(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value)
)

;; This parse function will be used to pass in a CSV and then replace the commas and the new
;; lines in order to make arrays of each row in the CSV

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")
  )
)

; (defn save
;   "Save suspect list as CSV"  
; )

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
  rows))

(defn validate-glitter
  "Validate glitter has a value and is a number"
  [value]
  (instance? Integer value)
)

(defn validate-name
  "Validate name has a value and is a string"
  [value]
  (instance? String value)
)

(def validations {:name validate-name :glitter-index validate-glitter})

(def map-suspects (mapify (parse (slurp filename))))

(defn append-suspect
  "Appends a name to the suspect list in the CSV File"
  [suspect]
  (parse (slurp filename))
)

(defn extract-names
  "Returns a list of names based on maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map :name rows)
)

(defn glitter-filter
  [minimum-glitter records]
  (extract-names (filter #(>= (:glitter-index %) minimum-glitter) records))
)  

(glitter-filter 3 map-suspects)
