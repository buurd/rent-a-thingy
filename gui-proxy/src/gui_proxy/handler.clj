(ns gui-proxy.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clj-http.client :as client]
            [gui-proxy.db :as db]))

(defn log-get-request [type url]
  (db/request-info type url))

(defn forward-google-search [query]
  (let [url (str "http://www.google.se/#q" query)]
    (log-get-request :get url)
    (:body (client/get (str "http://www.google.se/#q" url)))))

(defn home []
  (str "Rent a thingy" " " (db/count-request) " requests handled"))

(defroutes app-routes
  (GET "/" []
   (log-get-request :get "/")
   (home))
  (GET "/search/:query" [query] (forward-google-search query))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


