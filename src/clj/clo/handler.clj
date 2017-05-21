(ns clo.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [clo.layout :refer [error-page]]
            [clo.routes.home :refer [home-routes]]
            [clo.routes.services :refer [service-routes restricted-service-routes]]
            [compojure.route :as route]
            [clo.env :refer [defaults]]
            [mount.core :as mount]
            [clo.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    #'service-routes
    (wrap-routes #'restricted-service-routes middleware/wrap-auth)
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
