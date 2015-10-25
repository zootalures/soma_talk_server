(ns clj-aspviz.solver-client
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [cljs-http.client :as http]
            [goog.events :as events]
            [cljs.core.async :refer [put! <!  >! chan timeout]]
            ))

(defn solve-program
  "solves a program returns a channel which yeilds the answer sets "
  [prog]
  (let [c (chan)]
    (go (let [result
              (<! (http/post "/solver" {:body prog :headers {:content-type "text/plain"}}))]
          (println "got result " result)

          (>! c  result)))
    c))

