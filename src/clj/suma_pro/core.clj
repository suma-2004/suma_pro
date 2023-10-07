(ns suma_pro.core
  (:gen-class)
  (:require [system.repl :refer [set-init! go]]
            [suma_pro.system :refer [prod]]))

(defn -main
  "Start a production system."
  [& args]
  (if-let [system (first args)]
    (do (require (symbol (namespace (symbol system))))
        (set-init! (resolve (symbol system))))
    (set-init! #'prod))
  (go))

