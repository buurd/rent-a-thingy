(ns rent-a-thingy-req.current-user
  (:require [clj-http.client :as client]
            [clojure.test :refer :all]
            [clj-time.core :as time])
  (:import (java.util UUID)))

(deftest current-user
  "Verfifera att vi kan registera en användare och sedan kan utforska användaren"
  (testing "user-with-registration-only"
    (let [uuid (.toString (UUID/randomUUID))]
      (let [response
            (client/post "http://localhost:3000/register-user/register" {:form-params {:username uuid :adress "adress" :ort "ort"} :throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (is (= (:status response) 200) "Skapar användare"))
      (let [response (client/get (str "http://localhost:3000/current-user/get/" uuid "/" (.toString (time/now))) {:throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (println (str "http://localhost:3000/current-user/get/" uuid "/" (.toString (time/now))))
        (is (= (:status response) 200) "Hämta anvädare 1")
        (is (= uuid (:username (read-string (:body response)))) "Söker efter den skapade användaren")
        (let [content (read-string (:content (read-string (:body response))))]
          (is (= uuid (get content "username")) "username 1")
          (is (= "adress" (get content "adress")) "adress 1")
          (is (= "ort" (get content "ort")) "ort 1")))))

  (testing "user-with-both-registration-and-info"
    (let [uuid (.toString (UUID/randomUUID))]
      (println "uuid2: " uuid)
      (let [response
            (client/post "http://localhost:3000/register-user/register" {:form-params {:username uuid :adress "adress" :ort "ort"} :throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (is (= (:status response) 200) "Skapar användare"))
      (let [response (client/post "http://localhost:3000/user-info/add" {:form-params {:username uuid :telefon "telefon"} :throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (is (= (:status response) 200) "Uppdaterar användare")
        (let [response (client/get (str "http://localhost:3000/current-user/get/" uuid "/" (.toString (time/now))) {:throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
          (println "RESPONSE: " response)
          (is (= (:status response) 200) "Hämta användare 2")
          (is (= uuid (:username (read-string (:body response)))) "Söker efter den skapade användaren 2")
          (let [content (read-string (:content (read-string (:body response))))]
            (println "CONTENT: " content)
            (is (= uuid (get content "username")) "username2")
            (is (= "adress" (get content "adress")) "adress2")
            (is (= "ort" (get content "ort")) "ort2")
            (is (= "telefon" (get content "telefon")) "telefon2"))))))

  (testing "user-with-registration-and-two-info"
    (let [uuid (.toString (UUID/randomUUID))]
      (println "uuid3: " uuid)
      (let [response
            (client/post "http://localhost:3000/register-user/register" {:form-params {:username uuid :adress "adress" :ort "ort"} :throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (is (= (:status response) 200) "Skapar användare"))
      (let [response (client/post "http://localhost:3000/user-info/add" {:form-params {:username uuid :telefon "telefon" :ort "ort2"} :throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (is (= (:status response) 200) "Uppdaterar användare första gången"))
      (let [response (client/post "http://localhost:3000/user-info/add" {:form-params {:username uuid :telefon "telefon2"} :throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (is (= (:status response) 200) "Uppdaterar användare första gången"))
      (let [response (client/get (str "http://localhost:3000/current-user/get/" uuid "/" (.toString (time/now))) {:throw-exceptions false :socket-timeout 2000 :conn-timeout 2000})]
        (is (= (:status response) 200) "Hämtar användare 3")
        (is (= uuid (:username (read-string (:body response)))) "Söker efter den skapade användaren")
        (let [content (read-string (:content (read-string (:body response))))]
          (is (= uuid (get content "username")) "username3")
          (is (= "adress" (get content "adress")) "adress3")
          (is (= "ort" (get content "ort2")) "ort3")
          (is (= "telefon" (get content "telefon2")) "telefon3"))))))