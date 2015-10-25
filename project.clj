(defproject aspviz-server "0.1.0-SNAPSHOT"
  :description "Visualising answer set programs"
  :url "http://zootalures.github.io/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-pdo "0.1.1"]]
  :dependencies [
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [http-kit "2.1.18"]
                 [ring/ring-devel "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure "1.4.0"]
                 [clj-ansprog "0.1.0-SNAPSHOT"]
                 [hiccup "1.0.4"]
                 [cljs-webgl "0.1.5-SNAPSHOT"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljs-http "0.1.21"]
                 [com.taoensso/sente "1.6.0"]]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src-cljs"]
                        :compiler {:output-to "resources/public/js/app.js"
                                   :output-dir "resources/public/js/out"
                                   :optimizations :none
                                   :source-map true}}]}

  :aliases {"up" ["pdo" "cljsbuild" "auto" "dev," "run" ]}
  :source-paths ["src"]
  :main clj-aspviz.server
  )
