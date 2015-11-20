(ns register-user.db
  (:import (java.util Date)))

(use 'korma.db)

(defdb db (sqlite3 {:db "db/register-user.db"}))

(use 'korma.core)
(defentity user-registration)


(defn insert-registration [username content]
  (insert user-registration (values {
                                     :registration-date (.toString (Date.))
                                     :username          username
                                     :content           content})))

(defn select-registration [username]
  (select user-registration
          (fields :username :content :registration-date :id)
          (where {:username username}) (limit 1)))
