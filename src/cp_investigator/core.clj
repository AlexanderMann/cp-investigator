(ns cp-investigator.core
  (:gen-class)
  (:require [cp-investigator.class-path :as cp]))

(defn -main
  [& _]
  ;; Initialize configuration
  (println (cp/get-path) cp/java-executable)
  (println "exit status of alternate:" (cp/wait-on-process))
  (System/exit 0))
