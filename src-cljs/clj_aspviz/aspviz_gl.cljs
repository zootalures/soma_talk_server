(ns clj-aspviz.aspviz-gl
  (:require [cljs-webgl.context :as context]
            [cljs-webgl.shaders :as shaders]
            [cljs-webgl.constants.draw-mode :as draw-mode]
            [cljs-webgl.constants.data-type :as data-type]
            [cljs-webgl.constants.buffer-object :as buffer-object]
            [cljs-webgl.constants.shader :as shader]
            [cljs-webgl.buffers :as buffers]
            [cljs-webgl.typed-arrays :as ta]))

(defn bind-viz
  [element-id model]
  (let
    [gl (context/get-context (.getElementById js/document element-id))
     draw (fn [ frame continue]
            (-> gl
                )
            )
     ]
    1
    ))