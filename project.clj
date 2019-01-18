(defproject my-webapp "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.6.1"]
                 [ring/ring-json "0.4.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.6.0"]
                 [com.h2database/h2 "1.4.193"]
                 [metosin/spec-tools "0.8.2"]
                 [metosin/ring-swagger "0.26.1"]
                 [metosin/ring-swagger-ui "3.20.1"]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler my-webapp.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}}
  :main my-webapp.handler)

