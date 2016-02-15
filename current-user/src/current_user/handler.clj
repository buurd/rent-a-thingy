(ns current-user.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-http.client :as client]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn get-user-info [user timestamp]
  (let [registration (client/get (str "http://localhost:3001/registration/" user))]
    (let [info (client/get (str "http://localhost:3004/get/" user "/" timestamp))]
      (apply merge '(registration info)))))

(defroutes app-routes
  (GET "/get/:user/:timestamp" [user timestamp] (get-user-info user timestamp))
  (GET "/" [] "Rent a thingy - current-user")
  (route/not-found "Not Found - current-user"))

(def app
  (wrap-defaults app-routes site-defaults))
