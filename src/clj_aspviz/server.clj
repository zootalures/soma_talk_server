(ns clj-aspviz.server
    (require [clj-aspviz.routes :as routes]
             [ring.middleware.reload :as reload]
             [org.httpkit.server :as http]
             [compojure.handler :as handler] ))


(defn -main
      [ & args]
      (println "starting server ")
      (http/run-server
        (reload/wrap-reload (handler/site #'routes/main-routes)) {:port 8081 }))