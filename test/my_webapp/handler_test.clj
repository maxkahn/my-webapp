(ns my-webapp.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [my-webapp.handler :refer :all]
            [domain.location :refer :all]
            [clojure.string :as str]
            [my-webapp.db :as db]
            [clojure.spec.alpha :as s]))

(defn- mock-add-location-to-db
  [x y]
  (s/gen ::loc-id)
  )

(defn- mock-get-xy
  [loc-id]
  (assoc (s/gen ::location) :loc-id loc-id))

(defn- mock-get-all-locations
  []
  (s/gen ::locations))

(deftest test-app
  (binding [db/add-location-to-db mock-add-location-to-db
            db/get-xy mock-get-xy
            db/get-all-locations mock-get-all-locations]
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (str/starts-with? (get-in response [:headers "Content-Type"]) "text/html"))
      ))

  (testing "adding new location view"
    (let [response (app (mock/request :get "/add-location"))]
      (is (= (:status response) 200))
      (is (str/starts-with? (get-in response [:headers "Content-Type"]) "text/html"))
      ))

  ;(testing "adding new location"
  ;  (let [response (app (mock/request :post "/add-location" {:params {:x 100 :y 2}}))]
  ;    (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))))
