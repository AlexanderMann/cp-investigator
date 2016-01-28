(ns cp-investigator.class-path
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import [java.lang ProcessBuilder$Redirect]))

(def java-executable
  (str (System/getProperty "java.home")
       "/bin/java"))

(defn dummy [] nil)

(defn get-path
  []
  (if-let [j (-> *ns*
                 class
                 .getProtectionDomain
                 .getCodeSource
                 .getLocation)]
    (.getPath j)
    (System/getProperty "java.class.path")))

(defn create-process-args
  []
  (let [args (->> [java-executable
                   "-cp"
                   (get-path)
                   "clojure.main"
                   "--main"
                   "cp-investigator.alternate"]
                  (map str)
                  (into-array String))]
    (println "Preparing processing with args:" (map str args))
    args))

(defn error-message
  [process]
  (with-open [rdr (io/reader (.getErrorStream process))]
    {:exit-value  (.exitValue process)
     :process-log (->> rdr
                       line-seq
                       (take-last 10)
                       (string/join "\n"))}))

(defn wait-on-process
  []
  (let [process (-> (create-process-args)
                    ProcessBuilder.
                    (.redirectInput ProcessBuilder$Redirect/INHERIT)
                    (.redirectOutput ProcessBuilder$Redirect/INHERIT)
                    .start)]
    (println "Process started.")
    (.waitFor process)
    (println "Process ended")
    (error-message process)))
