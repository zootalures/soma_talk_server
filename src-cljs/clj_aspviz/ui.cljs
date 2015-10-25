(ns clj-aspviz.ui
  (:require [clj-aspviz.solver-client :as solver]
            [clj-aspviz.aspviz-gl :as aspviz]))

(defn by-id
  [elem]
  (.getElementById js/document elem))

(defn runSolve
  []
  (js/alert "Running solve"))

(defn init
  []
  (set! (. (by-id "solveit") -click) runSolve))