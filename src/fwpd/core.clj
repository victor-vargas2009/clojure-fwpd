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
(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value)
)

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")
  )
)

(defn transform-rows
  [rows]
  (map (fn [row]
        (println "Row: " row)) rows)
)

(transform-rows (parse (slurp filename)))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
  rows))
