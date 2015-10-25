(ns clj-aspviz.routes
  (:use [compojure.route :only [files not-found resources]]
        [compojure.core :only [defroutes GET POST DELETE ANY context]]
        [org.httpkit.server :as http]
        [clj-ansprog.clingo-solver :as clingo]
        [clj-ansprog.core :as asp]
        [clj-aspviz.views :as views]))

(def solver (atom (clingo/create-clingo-solver {} )))

(defn solver-handler
  [req]
  (http/with-channel req ch
                     (let [ansprog (asp/input->program (:body req))]
                     (do
                       (send! ch {:status 200
                                  :body "{ \"anssets\"  : [ \n"
                                  :headers {"Content-type" "text/application-json"}}
                              false)
                       (println req)
                         (doseq [v (asp/anssets (asp/solve @solver ansprog))]
                           (send! ch (str "\"" (asp/all-terms v )  "\",\n") false )))
                       (send! ch "null\n]}"  true))))



(defroutes main-routes
           (GET "/" [] (fn [_] (views/solver-form)))
           (POST "/solver" [] solver-handler)
           (resources "/" )
           (not-found "Page not found"))
