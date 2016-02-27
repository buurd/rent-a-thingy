(ns current-user.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-http.client :as client]
            [clojure.edn :as edn]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn get-user-info [user timestamp]
  (println "get-user-info" )
  (let [registration (edn/read-string (:body (client/get (str "http://localhost:3001/registration/" user) {:socket-timeout 1000 :conn-timeout 500})))]
    (let [info (client/get (str "http://localhost:3002/get/" user "/" timestamp) {:socket-timeout 1000 :conn-timeout 500})]
      (let [info2 (edn/read-string (:body info))]
        (println "REGE: " registration)
        (println "INFO: " info2)
      (if (empty? info2)
        (str (edn/read-string (:content registration)))
        (str (apply merge (edn/read-string (:content (first info2))) (edn/read-string (:content registration)))))))))


(defroutes app-routes
           (GET "/get/:user/:timestamp" [user timestamp] (get-user-info user timestamp))
           (GET "/" [] "Rent a thingy - current-user")
           (route/not-found "Not Found - current-user"))

(def app
  (wrap-defaults app-routes site-defaults))

