(ns clo.test.db.core
  (:require [clo.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [clo.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'clo.config/env
      #'clo.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users
  (jdbc/with-db-transaction
    [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (let [result (:id (db/create-user2!
                        t-conn
                        {:username   "ssmith"
                         :first_name "Sam"
                         :last_name  "Smith"
                         :email      "sam.smith@example.com"}))]

      (is (= {:id         result
              :username   "ssmith"
              :first_name "Sam"
              :last_name  "Smith"
              :email      "sam.smith@example.com"
              :admin      false
              :last_login nil
              :is_active  true}
             (db/get-user t-conn {:id result}))))))
