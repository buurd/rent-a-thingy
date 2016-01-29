(ns gui-proxy.handler-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [ring.mock.request :as mock]
            [gui-proxy.handler :refer :all]
            [gui-proxy.db :as db]))


(deftest "non-forward"
  (with-redefs [db/count-request (constantly 42)
                db/insert-request-info (constantly 0)]
    (testing "home" (let [response (app (mock/request :get "/"))]
                      (is (= (:status response) 200))
                      (is (= (:body response) "Rent a thingy - gui-proxy 42 requests handled"))))
    (testing "not-found route"
      (let [response (app (mock/request :get "/invalid"))]
        (is (= (:status response) 404)))))

  (testing "forward-register-user-get"
    (with-redefs [db/insert-request-info (constantly 0)
                  client/get (fn [url] {:status 200, :body "abc"})]
      (let [response (app (mock/request :get "/register-user/get/asd"))]
        (println response)
        (is (= (:status response) 200))))))




