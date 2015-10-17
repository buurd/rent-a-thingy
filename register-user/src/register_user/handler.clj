(ns register-user.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
           (POST "/register-user/" {body :body} body)
           (GET "/" [] "Rent a thingy - register-user")
           (GET "/test/:message" [message] (str "echo: " message))
           (route/not-found "register-user: Not Found"))

(def app
  (routes app-routes site-defaults))

(defn fake-request [routes uri method & params]
  (let [localhost "127.0.0.1"]
    (routes {:server-port 80
             :server-name localhost
             :remote-addr localhost
             :uri uri
             :scheme :http
             :headers (or params {})
             :request-method method})))