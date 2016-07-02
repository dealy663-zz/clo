(ns user
  (:require [mount.core :as mount]
            [clo.figwheel :refer [start-fw stop-fw cljs]]
            clo.core))

(defn start []
  (mount/start-without #'clo.core/repl-server))

(defn stop []
  (mount/stop-except #'clo.core/repl-server))

(defn restart []
  (stop)
  (start))


