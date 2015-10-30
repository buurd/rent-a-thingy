(ns register-user.db)

(use 'korma.db)

(defdb db (sqlite3 {:db ".db/register-user.db"}))

(use 'korma.core)
(defentity user-registration)


(defn insert-registration [username content]
  (insert user-registration (values {
                                     :registration-date (.toString (java.util.Date.))
                                     :username          username
                                     :content           content})))

(defn select-registration [username]
  (first (select user-registration
                (fields :username :content :registration-date :id)
                (where {:username username} ))))
