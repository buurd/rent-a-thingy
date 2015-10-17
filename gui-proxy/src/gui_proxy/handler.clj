(ns gui-proxy.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clj-http.client :as client]
            [gui-proxy.db :as db]))

(defn log-request [type url]
  (db/insert-request-info type url))

(defn forward-google-search [query]
  (let [url (str "http://www.google.se/#q" query)]
    (log-request ":get" url)
    (client/get (str "http://www.google.se/#q" url))))

(defn forward-register-user [body]
  (let [url (str "http://localhost:3001/register-user/")]
    (print body)
    (log-request ":post" url)
    (client/post url {:body body})))

(defn forward-register-user-test [message]
  (let [url (str "http://localhost:3001/test/" message)]
    (log-request ":get"  url)
    (:body (client/get url))))

(defn home []
  (log-request ":get" "/")
  (str "Rent a thingy - gui-proxy" " " (db/count-request) " requests handled"))

(defroutes app-routes
           (GET "/search/:query" [query] (forward-google-search query))
           (POST "/register-user/register" {body :body} (forward-register-user body))
           (GET "/register-user/test/:message" [message] (forward-register-user-test message))
           (GET "/" []
             (log-request ":get" "/")
             (home))
           (route/not-found "gui-proxy: Not Found"))

(def app
  (routes app-routes site-defaults))


