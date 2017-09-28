(ns random-pairs.views.home
  (:require [random-pairs.api :as api])
  (:use [hiccup.form]
        [hiccup.element :only (link-to)]
        [hiccup.page :only (html5 include-css include-js)]))

(defn application [title & content]
  (html5 {:lang "en"}
         [:head
          [:title title]
          (include-css "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css")
          #_(include-js "js/script.js")
          [:body
           [:div {:class "container"} content]]]))

(defn index []
  [:div {:id "content"}
   (let [pairs (api/get-pairs {:body {:name ["Matus" "Joe" "Alexander" "Mike" "PJ" "Brady" "Filip"]}})]
     [:table.table
      [:thead [:tr
               [:th "#"]
               [:th "name 1"]
               [:th "name 2"]]]
      [:tbody (for [[index [name1 name2]] (map-indexed vector pairs)]
                [:tr
                 [:th {:scope "row"} (inc index)]
                 [:td name1]
                 [:td name2]])]])])



