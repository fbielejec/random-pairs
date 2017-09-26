(ns user
  (:require [com.stuartsierra.component :as component]
            [random-pairs.system :as system]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :as repl]
            [clojure.tools.namespace.repl :refer [refresh set-refresh-dirs]]))

(remove-method clojure.core/print-method com.stuartsierra.component.SystemMap)

(defonce the-system nil)

(defn start []
  (alter-var-root #'the-system
                  (fn [old]
                    (when old
                      (system/stop old))
                    (-> (system/create-system)
                        (system/start)))))
(defn stop []
  (alter-var-root #'the-system
                  (fn [old]
                    (when old
                      (system/stop old)))))
(defn restart []
  (stop)
  (start))

(defn reset []
  (stop)
  (set-refresh-dirs "src" "dev")
  (refresh :after 'user/start))
