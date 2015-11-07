(ns clj-aspviz.routes
  (:require [compojure.route :as rt]
            [compojure.core :refer [defroutes GET POST DELETE ANY context]]
            [ring.util.request :as req]
            [ring.util.response :as resp]
            [org.httpkit.server :as http]
            [clj-ansprog.clingo-solver :as clingo]
            [clj-ansprog.core :as asp]
            [clj-ansprog.rotations :as rot]
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


(defn pieces-rotations-handler
  "generates a rotation/translation map for the pieces"
  [req]
  (let [program (asp/input->program "resources/public/examples/3dpuzzle/pieces/generatepieces.lp")]
    (as-> program $
          (asp/solve @solver $)
          (asp/anssets $)
          (map asp/all-terms $)
          (map rot/gen-rotations $)
          (into #{} $)
          (map first $)
          (map rot/gen-piece-locations $)
          (map-indexed (fn [idx allparts]
                         (map (fn [parts] (tools/nest-terms parts idx)) allparts)) $)
          (mapcat identity $)
          (mapcat identity $)
          (tools/terms->prog $)
          (resp/response $)
          (resp/content-type $ "text/plain")
          )))


(defn show-rotations-handler
  "generates a rotation/translation map for the pieces"
  [{
    {:keys [n bs]
     :or   {n 1 bs true}} :params
    :as                   req}]
  (let [program (asp/input->program "resources/public/examples/3dpuzzle/pieces/generatepieces.lp")]
    (as-> program $
          (asp/solve @solver $)
          (asp/anssets $)
          (into [] $)
          (nth $ (Integer/parseInt n))
          (asp/all-terms $)
          (rot/gen-rotations $)
          (map asp/->InMemAnswerSet $)
          (tools/nest-solutions $)
          (vec $)
          (prn-str $)
          (resp/response $)
          (resp/content-type $ "application/edn")
          )))


(defn show-non-somarotations-handler
  "generates a rotation/translation map for the pieces"
  [{
    {:keys [n bs]
     :or   {n 1 bs true}} :params
    :as                   req}]
  (let [program (asp/input->program "resources/public/examples/3dpuzzle/notsoma/pieces.lp")]
    (as-> program $
          (asp/solve @solver $)
          (asp/anssets $)
          (first $)
          (asp/all-terms $)
          (group-by (fn [[_ n loc]] n) $)
          (vals $)
          (map  (partial map (fn [[_ n loc ]]  [:part loc])) $ )
          (map rot/gen-piece-locations $)
          (map-indexed (fn [idx allparts]
                         (map (fn [parts] (tools/nest-terms parts idx)) allparts)) $)
          (mapcat identity $)
          (mapcat identity $)
          (tools/terms->prog $)
          (resp/response $)
          (resp/content-type $ "text/plain")
          )))

(defn puzzle-solution-handler
  "Generates solutions"
  [{
    {:keys [n fmt]
     :or   {n   100
            fmt "edn"}} :params
    :as                 req}]
  (let [program (asp/combine-programs
                  [(asp/input->program "resources/public/examples/3dpuzzle/puzzle/piecerotations.lp")
                   (asp/input->program "resources/public/examples/3dpuzzle/puzzle/puzzlesolver.lp")])]
    (as-> program $
          (asp/solve @solver $ {:num-solutions n})
          (asp/anssets $)
          (map asp/all-terms $)
          (prn-str $)
          (resp/response $)
          (resp/content-type $ "application/edn"))))


(defn notsoma-puzzle-solution-handler
  "Generates solutions"
  [{
    {:keys [n fmt]
     :or   {n   100
            fmt "edn"}} :params
    :as                 req}]
  (let [program (asp/combine-programs
                  [(asp/input->program "resources/public/examples/3dpuzzle/notsoma/rotations.lp")
                   (asp/input->program "resources/public/examples/3dpuzzle/puzzle/puzzlesolver.lp")])]
    (as-> program $
          (asp/solve @solver $ {:num-solutions n})
          (asp/anssets $)
          (map asp/all-terms $)
          (prn-str $)
          (resp/response $)
          (resp/content-type $ "application/edn"))))


(defn pieces-stage-handler
  [{
    {:keys [stage n bs fmt]
     :or   {n   100 bs false
            fmt "edn"}} :params
    :as                 req}]

  (println "solving stage " stage " for " n " answersets ")

  (let [program (-> (asp/input->program "resources/public/examples/3dpuzzle/pieces/generatepieces.lp")
                    (asp/filter-stage (Integer/parseInt stage)))]

    (println "program " (clojure.string/join "\n" (asp/prog-as-lines program)))
    (as-> program $
          (asp/solve @solver $ {:num-solutions n})
          (if bs
            (rot/generate-parts-cmd $)
            (-> $
                (asp/anssets)
                (tools/nest-solutions)))
          (vec $)
          (case fmt
            "lp"
            (->
              (tools/terms->prog $)
              (resp/response)
              (resp/content-type "text/plain"))
            (->
              (prn-str $)
              (resp/response)
              (resp/content-type "application/edn"))))))



(defn pieces-prog-handler
  [{{:keys [stage]} :route-params :as req}]
  (println "filtering program to stage " stage " " req)
  (as-> (asp/input->program "resources/public/examples/3dpuzzle/pieces/generatepieces.lp") $
        (asp/filter-stage $ (Integer/parseInt stage))
        (asp/prog-as-lines $)
        (clojure.string/join "\n" $)
        (resp/response $)
        (resp/content-type $ "text/plain")))

(defroutes main-routes
           (GET "/" [] (fn [_] (views/solver-form)))
           (GET "/piecerotations" [] pieces-rotations-handler)
           (GET "/showrotations" [] show-rotations-handler)
           (GET "/shownonsomarotations" [] show-non-somarotations-handler)

           (GET "/pieces-prog/:stage" [stage] pieces-prog-handler)
           (GET "/pieces-stage/:stage" [stage] pieces-stage-handler)
           (GET "/puzzle-solution" [stage] puzzle-solution-handler)
           (GET "/notsoma-puzzle-solution" [stage] notsoma-puzzle-solution-handler)

           (POST "/solver" [] solver-handler)
           (rt/resources "/")
           (rt/not-found "Page not found"))
