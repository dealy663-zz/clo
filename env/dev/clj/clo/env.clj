(ns clo.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [clo.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[clo started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[clo has shut down successfully]=-"))
   :middleware wrap-dev})
