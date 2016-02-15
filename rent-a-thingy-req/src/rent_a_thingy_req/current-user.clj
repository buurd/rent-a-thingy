(ns rent-a-thingy-req.current-user
  (:require [clj-http.client :as client]
            [clojure.test :refer :all]
            [clj-time.core :as time])
  (:import (java.util UUID)))

(deftest current-user
  "Verfifera att vi kan registera en användare och sedan kan utiforska användaren"
  (testing "user-with-registration-only"
    (let [uuid (.toString (UUID/randomUUID))]
      (let [response
            (client/post "http://localhost:3000/register-user/register" {:form-params {:username uuid :adress "adress" :ort "ort"} :throw-exceptions false})]
        (is (= (:status response) 200) "Skapar användare"))
      (let [response (client/get (str "http://localhost:3000/current-user/get/" uuid "/" (.toString (time/now))) {:throw-exceptions false})]
        (is (= (:status response) 200))
        (is (= uuid (:username (read-string (:body response)))) "Söker efter den skapade användaren")
        (let [content (read-string (:content (read-string (:body response))))]
          (is (= uuid (get content "username")))
          (is (= "adress" (get content "adress")))
          (is (= "ort" (get content "ort")))))))

  (testing "user-with-both-registration-and-info med nytt fält"
    (let [uuid (.toString (UUID/randomUUID))]
      (let [response
            (client/post "http://localhost:3000/register-user/register" {:form-params {:username uuid :adress "adress" :ort "ort"} :throw-exceptions false})]
        (is (= (:status response) 200) "Skapar användare"))
      (let [response (client/post "http://localhost:3000/user-info/add" {:form-params {:username uuid :telefon "telefon"} :throw-exceptions false})]
        (is (= (:status response) 200) "Uppdaterar användare")
        (let [response (client/get (str "http://localhost:3000/current-user/get/" uuid "/" (.toString (time/now))) {:throw-exceptions false})]
          (is (= (:status response) 200))
          (is (= uuid (:username (read-string (:body response)))) "Söker efter den skapade användaren")
          (let [content (read-string (:content (read-string (:body response))))]
            (is (= uuid (get content "username")))
            (is (= "adress" (get content "adress")))
            (is (= "ort" (get content "ort")))
            (is (= "telefon" (get content "telefon")))))))))



