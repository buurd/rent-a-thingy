(ns rent-a-thingy-req.registration
  (:require [clj-http.client :as client]
            [clojure.test :refer :all])
  (:import (java.util UUID)))

(deftest find-registration
         "Verfifera att vi kan registera en användare och sedan kan utiforska användaren"
         (testing "register-find"
                  (let [uuid (.toString (UUID/randomUUID))]
                    (let [response
                          (client/get (str "http://localhost:3000/register-user/get/" uuid) {:throw-exceptions false})]
                      (is (= (empty? (:body response)) true ) "Sök efter ej skapad användare")
                    (let [response
                          (client/post "http://localhost:3000/register-user/register" {:form-params {:username uuid }:throw-exceptions false})]
                      (is (= (:status response 200))) "Skapar användare")
                    (let [response
                          (client/get (str "http://localhost:3000/register-user/get/" uuid) {:throw-exceptions false})]
                      (is (= uuid (:username (read-string (:body response)))) "Söker efter den skapade användaren"))))))