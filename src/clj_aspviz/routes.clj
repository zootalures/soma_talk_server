(ns clj-aspviz.routes
  (:use [compojure.route :only [files not-found resources]]
        [compojure.core :only [defroutes GET POST DELETE ANY context]]
        [ring.util.request :as req]
        [org.httpkit.server :as http]
        [clj-ansprog.clingo-solver :as clingo]
        [clj-ansprog.core :as asp]
        [clj-aspviz.views :as views]))

(def solver (atom (clingo/create-clingo-solver {})))

(defn body->prog
  [req]
  (cond
    (= "text/plain" (req/content-type req )) (asp/input->program (:body req))
    (contains? req :edn-params) (asp/combine-programs (map asp/string->program (get-in req [:edn-params :progs])))
    :else (throw (IllegalArgumentException. (str "Unknown type of body " (content-type req))))))

(defn solver-handler
  [req]
  (http/with-channel req ch
                     (let [ansprog (body->prog  req)]
                       (do
                         (send! ch {:status  200
                                    :body    (->>
                                               ansprog
                                               (asp/solve @solver)
                                               (asp/anssets)
                                               (map asp/all-terms)
                                               (prn-str))
                                    :headers {"Content-type" "application/edn"}})))))



(defroutes main-routes
           (GET "/" [] (fn [_] (views/solver-form)))
           (POST "/solver" [] solver-handler)
           (resources "/")
           (not-found "Page not found"))
