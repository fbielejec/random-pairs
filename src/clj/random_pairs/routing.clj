(ns random-pairs.routing
  (:require [cheshire.core :as json]
            [clojure.walk :as walk]
            [compojure.handler :as handler]
            [compojure.core :as compojure :refer [GET POST DELETE]]
            [compojure.route :as route]
            [ring.middleware.cors :as cors]
            [clojure.tools.logging :as logging]
            [random-pairs.api :as api]))

(defn make-json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn decode-body [body]
  (-> body
      slurp
      json/parse-string
      walk/keywordize-keys))

(defn wrap-decode-request-body [handler]
  (fn [request]
    (if-let [body (:body request)]
      (-> request
          (assoc :body (decode-body body))
          handler)
      (handler request))))

(defn web-handler []
  (-> (compojure/routes
       (POST "/api/names" [] #(make-json-response (api/get-pairs %)))
       (route/not-found "Page not found"))
      (wrap-decode-request-body)
      (cors/wrap-cors :access-control-allow-origin [#".*"]
                      :access-control-allow-methods [:get :post])))
