(ns user-info.db
  (:import (java.util Date)))

(use 'korma.db)

(defdb db (sqlite3 {:db "db/user-info.db"}))

(use 'korma.core)
(defentity user-info)


(defn add-info [username content]
  (insert user-info (values {
                 :request-date (.toString (Date.))
                 :username          username
                 :content           content})))

(defn select-info [username]
  (select user-info
          (fields :username :content :request-date :id)
          (where {:username username})))

