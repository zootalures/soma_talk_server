(defproject aspviz-server "0.1.0-SNAPSHOT"
  :description "Visualising answer set programs"
  :url "http://zootalures.github.io/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [
                 [org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [ring/ring-devel "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [fogus/ring-edn "0.3.0"]
                 [ring-cors "0.1.7"]
                 [javax.servlet/servlet-api "2.5"]
                 [compojure "1.4.0"]
                 [clj-ansprog "0.1.0-SNAPSHOT"]
                 [hiccup "1.0.4"]
                 [org.clojure/core.memoize"0.5.7"]]


  :source-paths ["src"]
  :main clj-aspviz.server
  )
