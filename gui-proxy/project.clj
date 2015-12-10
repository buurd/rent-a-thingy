(defproject gui-proxy "0.1.0-SNAPSHOT"
  :description "Fasad mellan gui och backend i Rest a thingy"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [clj-http "2.0.0"]
                 [korma "0.4.0"]
                 [org.xerial/sqlite-jdbc "3.7.15-M1"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler gui-proxy.handler/app :auto-reload? true :port 3000}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
