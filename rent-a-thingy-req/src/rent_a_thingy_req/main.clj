(ns rent-a-thingy-req.main
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clj-http.client :as client]
            [rent-a-thingy-req.handler :refer :all]))

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

(defn -main
[]
(run-tests 'rent-a-thingy-req.main))
