(defproject current-user "0.1.0-SNAPSHOT"
  :description "Aggregerar information från register-user och user-info utifrån en given tidpunkt"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [clj-http "2.0.1"]
                 [korma "0.4.2"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler current-user.handler/app :auto-reload? true :port 3004}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
