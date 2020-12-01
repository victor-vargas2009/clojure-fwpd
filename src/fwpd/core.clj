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
