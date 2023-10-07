(ns ^:figwheel-hooks suma_pro.core
    (:require [cljs-utils.core :as utils :refer [by-id]]
              [cljs.core.match :refer-macros [match]]
              [reitit.frontend :as rf]
              [reitit.frontend.easy :as rfe]
              [react :as react]
              [react-dom :as dom]
              [suma_pro.routing-table :as rt]
              [goog.string :as gstring])    
    (:require-macros [cljs-utils.compilers.hicada :refer [html]])
    (:import [goog Uri]))

;;;;
(defn dev-mode? []
  (let [url (.parse Uri js/window.location)
        domain (.getDomain url)]
    (or (= 3085 (.getPort url)) (gstring/startsWith domain "localhost"))))

(if (dev-mode?)
  (enable-console-print!)
  (set! *print-fn*
        (fn [& args]
          (do))))
;;;

(defn faq []
  (html [:p "FAQ"]))

(defn support []
  (html [:p "Support"]))

(defn about []
  (html [:p "About"]))

(defn banner []
  (html [:div
         [:a {:href "/"} "Home | "]
         [:a {:href "/faq"} "FAQ | "]
         [:a {:href "/about"} "About | "]
         [:a {:href "/support"} "Support"]]))

(defn pages [props]
  (match [(:view (js->clj props :keywordize-keys true))]
         [:root] (html [:p "Home"])
         [:support] (html [:> support {}])
         [:faq] (html [:> faq {}])
         [:about] (html [:> about {}])))

(defn router []
  (let [[view setView] (react/useState :root)]
    (js/React.useEffect (fn []
                          (rfe/start!
                           (rf/router rt/routes)
                           (fn [m]
                             (setView (:name (:data m))))
                           {:use-fragment false})
                          (fn [] (println "router cleanup") #js [])))
    (html [:*
           [:> banner {}]
           [:> pages {:view view}]])))

(defn mount []
  (when-let [tag (by-id "app")]
    (let [root (html [:> router {}])
          strict-mode (react/createElement react/StrictMode nil root)]
      (if (dev-mode?)
        (.render (dom/createRoot tag) strict-mode)
        (.render (dom/createRoot tag) root)))))


(defonce init (mount))

(defn ^:before-load my-before-reload-callback []
    (println "BEFORE reload!"))

(defn ^:after-load my-after-reload-callback []
  (println "AFTER reload!")
  (mount))
