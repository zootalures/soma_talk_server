(ns clj-aspviz.server
    (require [clj-aspviz.routes :as routes]
             [ring.middleware.reload :as reload]
             [org.httpkit.server :as http]
             [compojure.handler :as handler]
             [ring.middleware.edn :as edn]))


(def aspviz-app
  (->
    (handler/site #'routes/main-routes)
    (reload/wrap-reload)
    (edn/wrap-edn-params)
   ))

(defn -main
      [ & args]
      (println "starting server ")
      (http/run-server aspviz-app
        {:port 8081 }))