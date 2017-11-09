(ns random-pairs.scheduler
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.logging :as logging]
            [clj-http.client :as http]
            [compojure.core :as compojure]
            [ring.adapter.jetty :as jetty]
            [random-pairs.routing :as routing]
            [random-pairs.utils :as utils]))

(defn set-interval [callback ms]
  (future (while true (do (Thread/sleep ms) (callback)))))

;; TODO: get on the heroku app itself
(defrecord Scheduler
    "Pokes the free Heroku Dyno every 10 minutes so that it does not go to sleep (sleep inteval is 30 minutes on free plan."
  [job]

  component/Lifecycle

  (start [this]
    (try
      (if job
        this
        (do
          (logging/info ::scheduler-start (str "Starting scheduler component"))
          (assoc this :job (set-interval #(http/get "https://random-pairs0x.herokuapp.com/") 600000))))
      (catch Throwable e
        (let [stack-trace (utils/with-err-str (.printStackTrace e))]
          (logging/fatal ::server-start (str "A fatal error occured while starting scheduler: " stack-trace))
          (System/exit -1)))))

  (stop [this]
    (if (not job)
      this
      (do
        (logging/info ::scheduler-stop "Stopping scheduler component")
        (future-cancel job)
        (assoc this :job nil)))))

(defn new []
  (map->Scheduler {:job nil}))
