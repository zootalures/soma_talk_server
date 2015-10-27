(ns clj-aspviz.ui
  (:require-macros [cljs.core.async.macros :refer [go alt!]])

  (:require [clj-aspviz.solver-client :as solver]
            [clj-aspviz.aspviz-gl :as aspviz]
            [cljs.core.async :as async :refer [<!]]
            [dommy.core :as dommy :refer-macros [sel sel1]]))


(def log println)


(defn runsolve
  [e]
  (let [prog  (dommy/value (sel1 "#program"))]
    (log "Running solve on " prog)
    (dommy/set-text! (sel1 "#result" ) "solving")
    (go
      (let [result (<! (solver/solve-programs [prog]))]
        (log "setting result to " result)
        (dommy/set-text! (sel1 "#result")   (map  (partial map solver/term-as-str) result) )
        ))))

(defn bigsolve
  [_]
  (go
    (let
      [progs [(<!(cljs-http.client/get "/examples/3dpuzzle/puzzle.aspviz"))
              (<!(cljs-http.client/get "/examples/3dpuzzle/puzzle.aspviz"))
              (<!(cljs-http.client/get "/examples/3dpuzzle/puzzle.aspviz"))]]
      (println "solving " progs)

      (let [result (<! (solver/solve-programs (map :body progs)))]
        (log "setting result to " result)
        (dommy/set-text! (sel1 "#result")   (map  (partial map solver/term-as-str) result) )
        ))))

(defn init
  []
  (enable-console-print!)
  (dommy/listen! (sel1 "#solveit") :click runsolve)
  (dommy/listen! (sel1 "#bigsolve") :click bigsolve))

(init)