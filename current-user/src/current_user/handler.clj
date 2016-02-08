(ns current-user.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-http.client :as client]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn get-user-info [user timestamp]
  (:body (client/get (str "http://localhost:3001/registration/" user))))

(defroutes app-routes
  (GET "/get/:user/:timestamp" [user timestamp] (get-user-info user timestamp))
  (GET "/" [] "Rent a thingy - current-user")
  (route/not-found "Not Found - current-user"))

(def app
  (wrap-defaults app-routes site-defaults))
