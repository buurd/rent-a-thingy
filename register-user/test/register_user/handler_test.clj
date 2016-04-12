(ns register-user.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [register-user.handler :refer :all]
            [register-user.db :as db]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200) "main 1")
      (is (= (:body response) "Rent a thingy - register-user") "main 2")))

  (testing "get registration"
    (with-redefs [db/select-registration (constantly '({:content "{:ort \"ort\" :username \"kalle\"}" :username "kalle"}))]
      (let [response (app (mock/request :get "/registration/kalle"))]
        (is (= (:status response) 200) "get registration 1")
        (is (= (:username response) "kalle") "get registration 2"))))

  (testing "get registration throw exception"
    (with-redefs [db/select-registration (fn [] (throw (Exception.)))]
      (let [response (app (mock/request :get "/registration/kalle"))]
        (is (= (:status response) 500) "get throw exception 1")
        (is (= (.startsWith (:body response) "SQL-fel")) "get throw exception 2"))))

  (testing "register-user"
    (with-redefs [db/insert-registration (constantly {:username "kalle"})]
      (let [response (app (mock/request :post "/register-user/" {:username "kalle"}))]
        (is (= (:status response) 200) "register-user"))))

  (testing "register-user throw exeption"
    (with-redefs [db/insert-registration (fn [] (throw (Exception.)))]
      (let [response (app (mock/request :post "/register-user/" {:username "kalle"}))]
        (is (= (:status response) 500) "register-user throw exception"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404) "not-found-route"))))

