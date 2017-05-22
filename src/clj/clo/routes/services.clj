(ns clo.routes.services
  (:require [clo.routes.services.auth :as auth]
          ;            [compojure.api.upload :refer [wrap-multipart-params TempFileUpload]]
            [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(s/defschema UserRegistration
  {:user-name      String
   :first-name     String
   :last-name      String
   :email          String
   :password       String
   :pass-confirm   String})

(s/defschema Result
  {:result                   s/Keyword
   (s/optional-key :message) String})


(declare service-routes)
(defapi service-routes
        {:swagger {:ui   "/swagger-ui"
                   :spec "/swagger.json"
                   :data {:info {:version     "1.0.0"
                                 :title       "AskClo API"
                                 :description "Public Services"}}}}
        (POST "/register" req
                :return Result
                :body [user UserRegistration]
                :summary "register a new user"
                (auth/register! req user)))

;(POST "/login" req
        ;      :header-params [authorization :- String]
        ;      :summary "log in the user and create a session"
        ;      :return Result
        ;      (auth/login! req authorization))
        ;
        ;(POST "/logout" []
        ;      :summary "remove user session"
        ;      :return Result
        ;      (auth/logout!))
        ;
        ;(GET "/gallery/:owner/:name" []
        ;     :summary "display user image"
        ;     :path-params [owner :- String name :- String]
        ;     (gallery/get-image owner name))
        ;
        ;(GET "/list-thumbnails/:owner" []
        ;     :path-params [owner :- String]
        ;     :summary "list thumbnails for images in the gallery"
        ;     :return [Gallery]
        ;     (gallery/list-thumbnails owner))
        ;
        ;(GET "/list-galleries" []
        ;     :summary "lists a thumbnail for each user"
        ;     :return [Gallery]
        ;     (gallery/list-galleries))


(declare restricted-service-routes)
(defapi restricted-service-routes
        {:swagger {:ui "/swagger-ui-private"
                   :spec "/swagger-private.json"
                   :data {:info {:version "1.0.0"
                                 :title "AskClo API"
                                 :description "Private Services"}}}})
        ;(POST "/upload" req
        ;      :multipart-params [file :- TempFileUpload]
        ;      :middleware [wrap-multipart-params]
        ;      :summary "handles image upload"
        ;      :return Result
        ;      (upload/save-image! (:identity req) file))
        ;
        ;(DELETE "/image/:thumbnail" {:keys [identity]}
        ;        :path-params [thumbnail :- String]
        ;        :summary "delete the specified file from the database"
        ;        :return Result
        ;        (gallery/delete-image!
        ;          identity thumbnail (clojure.string/replace thumbnail #"thumb_" "")))
        ;
        ;(DELETE "/account" {:keys [identity]}
        ;        (auth/delete-account! identity))

