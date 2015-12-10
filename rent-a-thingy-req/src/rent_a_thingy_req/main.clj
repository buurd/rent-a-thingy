(ns rent-a-thingy-req.main
  (:require [clojure.test :refer :all]
            [rent-a-thingy-req.handler :refer :all]
            [rent-a-thingy-req.is-services-running :refer :all]
            [rent-a-thingy-req.registration :refer :all]))


(defn -main
[]
  (run-tests 'rent-a-thingy-req.is-services-running)
  (run-tests 'rent-a-thingy-req.registration))