(ns user-info.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [user-info.handler :refer :all]
            [user-info.db :as db]))


(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "user-info"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "get user"
    (with-redefs [db/select-info (constantly "kalle")]
      (let [response (app (mock/request :get "/get/kalle"))]
        (is (= (:status response) 200))
        (is (= (:body response) "(kalle)")))))

  (testing "get user throw exception"
    (with-redefs [db/select-info (fn [] (throw (Exception.)))]
      (let [response (app (mock/request :get "/get/kalle/1"))]
        (is (= (:status response) 500))
        (is (= (.startsWith(:body response) "SQL-fel"))))))

  (testing "get user changeset"
    (with-redefs [db/select-info (constantly "kalle")]
      (let [response (app (mock/request :get "/get/kalle/1"))]
        (is (= (:status response) 200))
        (is (= (:body response) "(kalle)")))))

  (testing "get user changeset throw exception"
    (with-redefs [db/select-info (fn [] (throw (Exception.)))]
      (let [response (app (mock/request :get "/get/kalle"))]
        (is (= (:status response) 500))
        (is (= (.startsWith(:body response) "SQL-fel")))))

    (testing "add"
      (with-redefs [db/add-info (constantly {:username "kalle"})]
        (let [response (app (mock/request :post "/add" {:username "kalle"} ))]
          (is (= (:status response) 200)))))

    (testing "add"
      (with-redefs [db/add-info (fn [] (throw (Exception.)))]
        (let [response (app (mock/request :post "/add" {:username "kalle"} ))]
          (is (= (:status response) 500))))))
  )