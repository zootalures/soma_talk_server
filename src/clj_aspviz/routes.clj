(ns clj-aspviz.routes
  (:require [compojure.route :as rt]
            [compojure.core :refer [defroutes GET POST DELETE ANY context]]
            [ring.util.request :as req]
            [ring.util.response :as resp]
            [org.httpkit.server :as http]
            [clj-ansprog.clingo-solver :as clingo]
            [clj-ansprog.core :as asp]
            [clj-aspviz.views :as views]
            [clj-ansprog.tools :as tools]
            [clojure.core.memoize :as memo]))

(def solver (atom (clingo/create-clingo-solver {})))

(defn body->prog
  [req]
  (cond
    (= "text/plain" (req/content-type req)) (asp/input->program (:body req))
    (contains? req :edn-params) (asp/combine-programs (map asp/string->program (get-in req [:edn-params :progs])))
    :else (throw (IllegalArgumentException. (str "Unknown type of body " (req/content-type req))))))

(defn solver-handler
  [req]
  (http/with-channel req ch
                     (let [ansprog (body->prog req)]
                       (do
                         (http/send! ch {:status  200
                                         :body    (->>
                                                    ansprog
                                                    (asp/solve @solver)
                                                    (asp/anssets)
                                                    (map asp/all-terms)
                                                    (prn-str))
                                         :headers {"Content-type" "application/edn"}})))))

(def memo-get-parts
  (memo/ttl
    (fn []
      (as-> (asp/input->program "resources/public/examples/3dpuzzle/pieces/generatepieces.lp") $
            (asp/solve @solver $)
            (tools/generate-parts-cmd $)))
    :ttl/threshold 60000))

(defn pieces-handler
  [req]
    (as-> (memo-get-parts) $
          (vec $)
          (prn-str $)
          (resp/response $)
          (resp/content-type $ "application/edn")))

(defroutes main-routes
           (GET "/" [] (fn [_] (views/solver-form)))
           (GET "/pieces" [] pieces-handler)
           (POST "/solver" [] solver-handler)
           (rt/resources "/")
           (rt/not-found "Page not found"))
