(ns random-pairs.server
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.logging :as logging]
            [compojure.core :as compojure]
            [ring.adapter.jetty :as jetty]
            [random-pairs.routing :as routing]
            [random-pairs.utils :as utils]))

(defrecord Server [host port server]

  component/Lifecycle

  (start [this]
    (try
      (if server
        this
        (do
          (logging/info ::server-start (str "Starting Server component - " "host: " host " port: " port))
          (let [server (jetty/run-jetty (routing/web-handler) {:host host :port port :join? false})]
            (assoc this :server server))))
      (catch Throwable e
        (let [stack-trace (utils/with-err-str (.printStackTrace e))]
          (logging/fatal ::server-start (str "A fatal error occured while starting server: " stack-trace))
          (System/exit -1)))))

  (stop [this]
    (if (not server)
      this
      (do
        (logging/info ::server-stop "Stopping Server component")
        (.stop server)
        (assoc this :server nil)))))

(defn new [{:keys [:host :port]}]
  (map->Server {:host host :port port :server nil}))
