(ns my-webapp.handler
  (:require [my-webapp.views :as views]
            [domain.location :as location]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]
            [spec-tools.swagger.core :as swagger]
            [ring.swagger.swagger-ui :as swagger-ui]
            [ring.util.http-response :refer [ok]]
            [clojure.spec.alpha :as s])
  (:gen-class))

(defroutes app-routes
           (swagger-ui/swagger-ui {:path "/swagger"})
           (GET "/swagger.json"
                []
             (ok (swagger/swagger-spec
                   {:swagger "2.0"
                    :tags    [{:name        "locations"
                               :description "sample app with locations"}]
                    :paths
                             {"/"                 {:get {::swagger/responses {}}}
                              "/add-location"     {:get  {::swagger/responses {200 {:description "Form to enter new location"}}}
                                                   :post {:summary             "Single location API"
                                                          :description         "Adds single new location"
                                                          ::swagger/parameters {:formData (s/keys :req-un [::location/x ::location/y])}
                                                          ::swagger/responses {200 {}}}}
                              "/location/:loc-id" {:get {:summary "Location retrieval"
                                                         :description "Retrieves coordinates of a single location"
                                                         ::swagger/parameters {:path (s/keys :req-un [::location/loc-id])}
                                                         ::swagger/responses {200 {:schema ::location/location
                                                                                   :description "Location found"}
                                                                              404 {:description "Not found"}}}}
                              "/all-locations"    {:get {:summary "All locations"
                                                         :description "List of all known locations"
                                                         ::swagger/responses {200 {:schema ::location/locations}}}}}})))
           (GET "/"
                []
             (views/home-page))
           (GET "/add-location"
                []
             (views/add-location-page))
           (POST "/add-location"
                 {params :params}
             (do
               (println "routed to POST with params: " params)
               (views/add-location-results-page params)))
           (GET "/location/:loc-id"
                [loc-id]
             (views/location-page loc-id))
           (GET "/all-locations"
                []
             (views/all-locations-page))
           (route/resources "/")
           (route/not-found "Not Found")
           )

(def app (wrap-json-response (wrap-defaults app-routes site-defaults)))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           5000))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))