(ns register-user-req.core
  (:require [clj-http.client :as client]
            [clojure.test :refer :all])
  (:import (java.util UUID)))

(def reqister-user-url "http://localhost:3001/register-user/")
(def registration-url "http://localhost:3001/registration/")

(defn register-user [username]
  (client/post reqister-user-url {:form-params {:username username }:throw-exceptions false}))

(defn registration [username]
  (client/get (str registration-url username)))

(defn create-and-find []
  (let [uuid (.toString (UUID/randomUUID)) responses {}]
    (-> (assoc responses :insert (register-user uuid))
        (assoc :select (registration uuid)))))

(defn double-insert []
  (let [uuid (.toString (UUID/randomUUID)) responses {}]
    (-> (assoc responses :first (register-user uuid))
        (assoc :second (register-user uuid )))))