(ns register-user-req.req
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [register-user.handler :refer :all]
            [register-user.db :as db]))

(deftest create-and-find-test
         (testing "create-and-find"
                  (let [responses (create-and-find)]
                    (is (= 200 (:status (:insert responses))))
                    (is (= 200 (:status (:select responses)))))))

(deftest duplicate-insert-test
         (testing "duplicate-insert"
                  (let [responses (double-insert)]
                    (is (= 200 (:status (:first responses))))
                    (is (= 500 (:status (:second responses)))))))
