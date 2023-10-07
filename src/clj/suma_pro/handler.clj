(ns suma_pro.handler
    (:require [reitit.ring :as ring]
              [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
              [ring.util.response :refer [response content-type]]
              [suma_pro.routing-table :refer [routes]]
              [suma_pro.html :as html]))

(defn bidi-routes [_]
  (let [index (fn [_] (-> (html/index)
                          response
                          (content-type "text/html")))]
    (ring/router
     (into [] (for [[r1 r2] routes] [r1 (assoc r2 :get index)])))))

(defn default-handler [_]
  (ring/routes
    (ring/create-resource-handler {:path "/" :root ""})
    (ring/create-default-handler)))

(def middleware {:middleware [[wrap-defaults site-defaults]]})
