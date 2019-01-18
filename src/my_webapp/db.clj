(ns my-webapp.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec {:dbtype "h2" :dbname "./my-webapp"})

(defn
  ;; dynamic so the function can be mocked for unit testing
  ^:dynamic
  add-location-to-db
  [x y]
  (let [results (jdbc/insert! db-spec :locations {:x x :y y})]
    (assert (= (count results) 1))
    (first (vals (first results)))))

(defn
  ;; dynamic so the function can be mocked for unit testing
  ^:dynamic
  get-xy
  [loc-id]
  (let [results (jdbc/query db-spec
                            ["select x, y from locations where id = ?" loc-id])]
    (assert (= (count results) 1))
    (first results)))

(defn
  ;; dynamic so the function can be mocked for unit testing
  ^:dynamic
  get-all-locations
  []
  (jdbc/query db-spec "select id, x, y from locations"))
