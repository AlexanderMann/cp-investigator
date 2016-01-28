(ns cp-investigator.alternate
  (:gen-class))

(defn -main
  [& _]
  ;; Initialize configuration
  (println "Alternate process reached")
  (System/exit 127))
