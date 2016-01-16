(ns rent-a-thingy-req.user-info
  (:require [clj-http.client :as client]
            [clojure.test :refer :all])
  (:import (java.util UUID)))
(deftest user-info
  "Hämta information som inte har några uppdateringar ännu"
  (testing "no-updates-found"
    (let [uuid (.toString (UUID/randomUUID))]
      (let [response (client/get (str "http://localhost:3000/user-info/get/" uuid) {:throw-exceptions false})]
                    (is (empty? (:change-list (read-string (:body response)))) "Listan ska vara tom")
                    (is (= uuid (:username (read-string (:body response)))) "Namnet på den sökta användaren ska alltid returneras")))))

