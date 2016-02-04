(ns register-user.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [register-user.handler :refer :all]
            [register-user.db :as db]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Rent a thingy - register-user"))))

  (testing "get registration"
    (with-redefs [db/select-registration (constantly "kalle")]
    (let [response (app (mock/request :get "/registration/kalle"))]
      (is (= (:status response) 200))
      (is (= (:body response) "kalle")))))

  (testing "get registration throw exception"
    (with-redefs [db/select-registration (fn [] (throw (Exception.)))]
      (let [response (app (mock/request :get "/registration/kalle"))]
        (is (= (:status response) 500))
        (is (= (.startsWith(:body response) "SQL-fel"))))))

  (testing "register-user"
    (with-redefs [db/insert-registration (constantly {:username "kalle"})]
      (let [response (app (mock/request :post "/register-user/" {:username "kalle"} ))]
        (is (= (:status response) 200)))))

  (testing "register-user throw exeption"
    (with-redefs [db/insert-registration (fn [] (throw (Exception.)))]
      (let [response (app (mock/request :post "/register-user/" {:username "kalle"} ))]
        (is (= (:status response) 500)))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
