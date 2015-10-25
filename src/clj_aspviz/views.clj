(ns clj-aspviz.views (:require
                       [hiccup
                        [page :refer [html5]]
                        [element :refer [javascript-tag]]
                        [page :refer [include-js]]]))

(defn solver-form
  []
  (html5 [:head
          [:title "ASPViz"]
          (include-js "/js/out/goog/base.js")
          (include-js "/js/app.js")
          ]
         [:body
          [:h1 "ASPViz"]
          [:div {:id "solverform"}
           [:textarea {:name "program"} "a:- not b. b:- not a."]
           [:button {:id "solveit"}  "Solve it"]]
          [:div {:id  "display"}
           [:canvas {:id "glcanvas"}]]
          [:script {:type "text/javascript" } "goog.require('clj_aspviz.ui'); clj_aspviz.ui.init();" ]]
         )
  )
