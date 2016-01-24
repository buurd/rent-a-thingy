(ns user-info.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.params :refer [wrap-params]]
            [user-info.db :as db]))

(defn validate-and-save! [body]
  (println "********************************************************************************")
  (println (str body))
  (println "********************************************************************************")
  (try (let [username (get body "username")]
         (db/add-info username (str body))
         (str "register-user validate and save" body)
         )
       (catch Exception e {:status 500 :body (str "SQL-fel insert " (.getMessage e))})))

(defn validate-and-get-user [username]
  (try
    (str "(" (apply str  (db/select-info username) )")")
    (catch Exception e {:status 500 :body (str "SQL-fel select " (.getMessage e))})))

(defroutes app-routes
  (GET "/get/:user" [user] (validate-and-get-user user))
  (GET "/get/:user/:update-id" [user update-id]  (validate-and-get-user user))
  (POST "/add" {params :params} (validate-and-save! params))
  (GET "/" [] "user-info")
  (route/not-found "Not Found"))

(def app
  (routes (wrap-params app-routes) site-defaults))

