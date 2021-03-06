(defproject random-pairs "0.1.0-SNAPSHOT"
  :description "Backend API + UI (if time and will permits) to randomly draw pairs of people."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.stuartsierra/component "0.3.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [hiccup "1.0.5"]
                 [compojure "1.6.0"]
                 [clj-http "3.7.0"]
                 [ring/ring-core "1.6.1"]
                 [ring/ring-jetty-adapter "1.6.1"]
                 [ring-middleware-format "0.7.2"]
                 [ring-cors "0.1.11"]]
  :min-lein-version "2.0.0"
  :main ^:skip-aot random-pairs.system
  :repl-options {:init-ns ^:skip-aot user}
  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[ring/ring-mock "0.3.0"]
                                  [org.clojure/tools.namespace "0.2.10"]]}
             :uberjar {:aot :all
                       :uberjar-name "random-pairs.jar"
                       :main random-pairs.system}})
