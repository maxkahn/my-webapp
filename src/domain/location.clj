(ns domain.location
  (:require [clojure.spec.alpha :as s]))

(s/def ::x int?)
(s/def ::y int?)
(s/def ::loc-id pos-int?)
(s/def ::location (s/keys :req [::x ::y ::loc-id]))
(s/def ::locations (s/coll-of ::location))