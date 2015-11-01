(ns clj-aspviz.views (:require
                       [hiccup
                        [page :refer [html5]]
                        [element :refer [javascript-tag]]
                        [page :refer [include-js]]]))

(defn solver-form
  []
  (html5 [:head
          [:title "ASPViz"]]
         [:body
          [:h1 "ASPViz"]
          [:div {:id "solverform"}
           "ceci c'est pas une web page "
          ]]
         )
  )

