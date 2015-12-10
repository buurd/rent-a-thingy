(ns rent-a-thingy-req.is-services-running
  (:require [clj-http.client :as client]
            [clojure.test :refer :all]))


(deftest is-services-running
         "Verifera att alla tjänster i systemet har startat och är igång"
         (testing "gui-proxy"
                  (let [response (client/get "http://localhost:3000" )]
                    (is (= (:status response) 200))))
         (testing "register-user"
                  (let [response (client/get "http://localhost:3001" )]
                    (is (= (:status response) 200))))
         (testing "user-info"
                  (let [response (client/get "http://localhost:3002")]
                    (is (= (:status response 200))))))
