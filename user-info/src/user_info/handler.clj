(ns user-info.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes

  (GET "/get/:user" [user] (str {:username user :change-list '()}))
  (GET "/get/:user/:update-id" [user update-id]  (str {:username user :change-list '()}))
  (GET "/" [] "user-info")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

