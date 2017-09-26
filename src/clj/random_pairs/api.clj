(ns random-pairs.api
  (:require [random-pairs.utils :as utils]
            [clojure.tools.logging :as logging]
            [ring.util.response :as response]))

(def +http-ok+ 200)
(def +http-internal-server-error+ 500)
(def +internal-server-error+ "Internal Server Error")
(def +http-bad-request+ 400)

(defn make-response
  [http-status body]
  (-> (response/response body)
      (response/status http-status)))

(defn get-pairs [{:keys [body] :as request}]
  (try
    (->> body
         vals
         first
         shuffle
         (partition 2))
    (catch Throwable e
      (do
        (logging/error (.getMessage e))
        (make-response +http-internal-server-error+ +internal-server-error+)))))

