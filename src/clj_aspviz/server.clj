(ns clj-aspviz.server
    (require [clj-aspviz.routes :as routes]
             [ring.middleware.reload :as reload]
             [org.httpkit.server :as http]
             [compojure.handler :as handler]
             [ring.middleware.edn :as edn]
             [ring.middleware.cors :refer [wrap-cors]]))


(def aspviz-app
  (->
    (handler/site #'routes/main-routes)
    (reload/wrap-reload)
    (edn/wrap-edn-params)
    (wrap-cors :access-control-allow-origin [#".*"]
               :access-control-allow-methods [:get :put :post :delete :head])
   ))

(defn -main
      [ & args]
      (println "starting server ")
      (http/run-server aspviz-app
        {:port 8081 }))