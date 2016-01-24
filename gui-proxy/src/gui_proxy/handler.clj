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
  (let [url (str "http://localhost:3001/register-user/"),
        bodystr (slurp body)]
    (log-request ":post" url)
    (client/request {:url url :body bodystr :method :post :content-type :application/x-www-form-urlencoded})))

(defn forward-register-user-test [message]
  (let [url (str "http://localhost:3001/test/" message)]
    (log-request ":get" url)
    (:body (client/get url))))


(defn forward-register-user-get [user]
  (let [url (str "http://localhost:3001/registration/" user)]
    (log-request ":get" url)
    (:body (client/get url))))

(defn home []
  (log-request ":get" "/")
  (str "Rent a thingy - gui-proxy" " " (db/count-request) " requests handled"))

(defn log-error []
  "gui-proxy - File not found")

(defn forward-user-info-get
  ([user]
   (let [url (str "http://localhost:3002/get/" user)]
     (log-request ":get" url)
     (:body (client/get url))))
  ([user update-id]
   (let [url (str "http://localhost:3002/get/" user "/" update-id)]
     (log-request ":get" url)
     (:body (client/get url)))))

(defn forward-user-info-add [body]
  (let [url (str "http://localhost:3002/add"),
        bodystr (slurp body)]
    (log-request ":post" url)
    (client/request {:url url :body bodystr :method :post :content-type :application/x-www-form-urlencoded})))


(defroutes app-routes
           (GET "/search/:query" [query] (forward-google-search query))
           (POST "/register-user/register" {body :body} (forward-register-user body))
           (GET "/register-user/get/:user" [user] (forward-register-user-get user))
           (GET "/register-user/test/:message" [message] (forward-register-user-test message))
           (GET "/user-info/get/:user" [user] (forward-user-info-get user))
           (GET "/user-info/get/:user/:update-id" [user update-id] (forward-user-info-get user update-id))
           (POST "/user-info/add" {body :body} (forward-user-info-add body))
           (GET "/" []
             (log-request ":get" "/")
             (home))
           (route/not-found (log-error)))

(def app
  (routes app-routes site-defaults))


