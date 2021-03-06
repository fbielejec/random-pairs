(ns random-pairs.system
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.logging :as logging]
            [random-pairs.server :as server]
            [random-pairs.scheduler :as scheduler]))

(defn system-map []
  (component/system-map
   :server (server/new {:host (or (System/getenv "HOST") "0.0.0.0")
                        :port (let [system-port (System/getenv "PORT")]
                                (if-not system-port
                                  8080
                                  (Integer. system-port)))})
   :scheduler (scheduler/new)))

(def dependency-map
  {})

(defn create-system
  []
  (logging/info ::create-system "Creating system")
  (component/system-using (system-map)
                          dependency-map))

(defn start
  [system]
  (logging/info ::start "Starting the application")
  (component/start system))

(defn stop
  [system]
  (logging/info ::stop "Stopping the application")
  (component/stop system))

(defn -main [& args]
  (let [system (create-system)]
    (start system)
    (logging/info ::main "Application fully functional")))
