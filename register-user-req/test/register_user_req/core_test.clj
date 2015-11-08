(ns register-user-req.core-test
  (:require [clojure.test :refer :all]
            [register-user-req.core :refer :all]))

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