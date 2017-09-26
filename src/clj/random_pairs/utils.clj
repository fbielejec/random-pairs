(ns random-pairs.utils
  (:require [clojure.string :as string]))

(defmacro with-err-str
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*err* s#]
       ~@body
       (str s#))))

(defn uuid []
  (-> (java.util.UUID/randomUUID)
      .toString
      keyword))
