(ns clo.routes.services.auth
  (:require [clo.db.core :as db]
            [ring.util.http-response :as response]
            [clo.validation :refer [registration-errors]]
            [buddy.hashers :as hashers]
            [clojure.tools.logging :as log])
  (:import (java.sql SQLException)
           (java.util Base64)
           (java.nio.charset Charset)))

(defn handle-registration-error [e user]
  (if (and
        (instance? SQLException e)
        (-> e
            (.getMessage)
            (.startsWith "ERROR: duplicate key value")))
    (response/precondition-failed
      {:result  :error
       :message (str "user " (:user-name user)
                     " already exists")})
    (do
      (log/error e)
      (response/internal-server-error
        {:result  :error
         :message (str "server error occurred while adding the user " (:user-name user)
                       ": "(.getMessage e))}))))

(defn register! [{:keys [session]} user]
  "Registers a new user with the system. If successful the new user info will be added to the session object.

   If successful, returns the numeric user-id that was created for the new user."
  (if-let [errors (registration-errors user)]
    (response/precondition-failed {:result :error
                                   :validation-errors errors})
    (try
      (let [user-id (:id (db/create-user!
                           (-> user
                               (dissoc :pass-confirm)
                               (update :password hashers/encrypt))))]
        (-> {:result :ok}
            (response/ok)
            (assoc :session (assoc session :identity (:user-name user)
                                           :user-id user-id))))
      (catch Exception e
        (handle-registration-error e user)))))

(defn decode-auth [encoded]
  (let [auth (second (.split encoded " "))]
    (-> (.decode (Base64/getDecoder) auth)
        (String. (Charset/forName "UTF-8"))
        (.split ":"))))

(defn authenticate [[id pass]]
  (when-let [user (db/get-user {:id id})]
    (when (hashers/check pass (:pass user))
      id)))

(defn login! [{:keys [session]} auth]
  (if-let [id (authenticate (decode-auth auth))]
    (-> {:result :ok}
        (response/ok)
        (assoc :session (assoc session :identity id)))
    (response/unauthorized {:result :unauthorized
                            :message "login failure"})))