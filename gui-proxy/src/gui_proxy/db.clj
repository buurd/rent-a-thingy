(ns gui-proxy.db)
(use 'korma.db)

(defdb db (sqlite3 {:db "db/gui-proxy.db"}))

(use 'korma.core)
(defentity request-info)


(defn insert-request-info [type url]
  (insert request-info (values {
                                :url url
                                :request-date (.toString (java.util.Date.))
                                :type type})))

(defn count-request []
  (:cnt (first (select request-info (aggregate (count :id) :cnt)))))