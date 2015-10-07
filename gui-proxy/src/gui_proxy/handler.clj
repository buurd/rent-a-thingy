(ns gui-proxy.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clj-http.client :as client]))

(defn forward-google-search [query]
  (:body (client/get (str "http://www.google.se/#q" query))))

(defroutes app-routes
  (GET "/" [] "Rent A Thingy")
           (GET "/search/:query" [query] (forward-google-search query))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


