(defproject rent-a-thingy-req "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [clj-http "2.0.0"]
                 [clj-time "0.11.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :main rent-a-thingy-req.main
  :ring {:handler rent-a-thingy-req.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
