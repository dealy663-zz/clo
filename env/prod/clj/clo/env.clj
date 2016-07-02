(ns clo.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[clo started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[clo has shut down successfully]=-"))
   :middleware identity})
