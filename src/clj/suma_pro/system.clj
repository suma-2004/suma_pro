(ns suma_pro.system
  (:require [system.core :refer [defsystem]]
            [com.stuartsierra.component :as component]
            [system.components
             [endpoint :refer [new-endpoint]]
             [handler :refer [new-handler]]
             [repl-server :refer [new-repl-server]]
             [undertow :refer [new-undertow]]]
            [suma_pro.handler :refer [bidi-routes default-handler middleware]]))

(defsystem base
  [:bidi-endpoint (component/using (new-endpoint bidi-routes) [])
   :handler (component/using (new-handler :default-handler default-handler :options middleware) [:bidi-endpoint])
   :http (component/using (new-undertow :port (Integer. ^String (System/getProperty "http.port"))) [:handler])])

(defn prod
  "Assembles and returns components for a production deployment"
  []
  (merge (base)
         (component/system-map
          :repl-server (new-repl-server :port (Integer. (System/getProperty "repl.port")) :bind (System/getProperty "repl.ip") :with-cider false)
          :cider-repl-server (new-repl-server :port (inc (Integer. (System/getProperty "repl.port"))) :bind (System/getProperty "repl.ip") :with-cider true))))
