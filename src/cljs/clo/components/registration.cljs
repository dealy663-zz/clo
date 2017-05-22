(ns clo.components.registration
  (:require [reagent.core :refer [atom]]
            [reagent.session :as session]
            [ajax.core :as ajax]
            [clo.validation :refer [registration-errors]]
            [clo.components.common :as c]))

(defn register! [fields errors]
  "Posts the registration request to the server."

  (reset! errors (registration-errors @fields))
  (when-not @errors
    (ajax/POST "/register"
               {:params @fields
                :handler
                        #(do
                           (session/put! :identity (:user-name @fields))
                           (reset! fields {}))
                :error-handler
                        #(reset!
                           errors
                           {:server-error (get-in % [:response :message])})})))

(defn registration-form []
  "Form for registering new users."

  (let [fields (atom {})
        error  (atom nil)]
    (fn []
      [c/modal
       [:div "Ask" [:em "Clo"] " Registration"]
       [:div
        [:div.well.well-sm
         [:strong "✱ required field"]]
        [c/text-input "user-name" :user-name "enter a user name" fields]
        [c/text-input "first-name" :first-name "enter the user's first name" fields]
        [c/text-input "last-name" :last-name "enter the user's last name" fields]
        [c/text-input "email" :email "enter a user's email address" fields]
        [c/password-input "password" :password "enter a password" fields]
        [c/password-input "password" :pass-confirm "re-enter the password" fields]
        (when-let [error (:server-error @error)]
          [:div.alert.alert-danger error])
        ]
       [:div
        [:button.btn.btn-primary
         {:on-click #(register! fields error)}
         "Register"]
        [:button.btn.btn-danger "Cancel"]]])))