(ns rent-a-thingy-req.current-user
  (:require [clj-http.client :as client]
            [clojure.test :refer :all]
            [clj-time.core :as time])
  (:import (java.util UUID)))

(deftest "current-user"
  "Verfifera att vi kan registera en anv�ndare och sedan kan utiforska anv�ndaren"
  (testing "user-with-registration-only"
    (let [uuid (.toString (UUID/randomUUID))]
      (let [response
            (client/post "http://localhost:3000/register-user/register" {:form-params {:username uuid} :throw-exceptions false})]
        (is (= (:status response) 200) "Skapar anv�ndare"))
      (let [response (client/get (str "http://localhost:3000/current-user/get/" uuid "/" (.toString (time/now))) {:throw-exceptions false})]
        (is (= (:status response) 200))
        (is (= uuid (:username (read-string (:body response)))) "S�ker efter den skapade anv�ndaren")))))



