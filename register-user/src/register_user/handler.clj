(ns register-user.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.params :refer [wrap-params]]
            [register-user.db :as db]))


(defn validate-and-save! [body]
  (try (let [username (get body "username")]
         (db/insert-registration username (str body))
         (str "register-user validate and save" body)
         )
       (catch Exception e {:status 500 :body (str "SQL-fel insert " (.getMessage e))})))


(defn validate-and-get-user [username]
  (try
    (apply str (db/select-registration username))
    (catch Exception e {:status 500 :body (str "SQL-fel select " (.getMessage e))})))

(defroutes app-routes
           (POST "/register-user/" {params :params} (validate-and-save! params))
           (GET "/registration/:username" [username] (validate-and-get-user username))
           (GET "/" [] "Rent a thingy - register-user")
           (route/not-found "register-user: Not Found"))

(def app
  (routes (wrap-params app-routes) site-defaults))

