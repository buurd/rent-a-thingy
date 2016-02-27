(ns rent-a-thingy-req.user-info
  (:require [clj-http.client :as client]
            [clojure.test :refer :all])
  (:import (java.util UUID)))


(deftest user-info
  "Hämta information som inte har några uppdateringar ännu"
  (testing "no-updates-found"
    (let [uuid (.toString (UUID/randomUUID))]
      (let [response (client/get (str "http://localhost:3000/user-info/get/" uuid) {:throw-exceptions false})]
        (is (empty? (read-string (:body response))) "Listan ska vara tom")

        ))))

(deftest user-info2
  "Skapa en ändring och hämta den"
  (testing "update found")
  (let [uuid (.toString (UUID/randomUUID))]
    (let [response
          (client/post "http://localhost:3000/user-info/add" {:form-params {:username uuid} :throw-exceptions false})]
      (is (= (:status response 200))) "Uppdatera användare")
    (let [response (client/get (str "http://localhost:3000/user-info/get/" uuid) {:throw-exceptions false})]
      (is (= uuid (:username (first (read-string (:body response))))) "Listan ska inte vara tom"))))
