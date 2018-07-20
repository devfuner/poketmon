(ns poketmon.core
  (:require
    [clj-time.core :as t]
    [clj-time.format :as f]
    [clj-time.periodic :as p])
  (:gen-class))

(use 'cronj.core)

(def url-format "http://image.chosun.com/premium/nie_newspaper/%s/c_medu_hanja/pokemon.jpg")

(def url-formatter (f/formatter "yyyy/MM/dd"))
(def file-formatter (f/formatter "yyyy_MM_dd"))

(defn get-url [date]
  (format url-format (f/unparse url-formatter date)))

(defn get-path [date]
  ;(format "/Users/devfuner/Dropbox/devcode/clojure/poketmon/resources/img/%s.jpg" (f/unparse file-formatter date)))
  (format "./resources/img/%s.jpg" (f/unparse file-formatter date)))

(defn copy-uri-to-file [date]
  (try
    (with-open [in (clojure.java.io/input-stream (get-url date))
                out (clojure.java.io/output-stream (get-path date))]
      (clojure.java.io/copy in out))
    (catch java.io.FileNotFoundException e (println (str "File Not Found " (f/unparse url-formatter date))))))



(def download-task
  {:id       "download-task"
   :handler  copy-uri-to-file
   :schedule "0 0 7 * * * *"})

(def cj (cronj :entries [download-task]))

;; (defn -main []
;;  (start! cj))


(copy-uri-to-file (t/date-time 2018 4 23))

;; (def take-cnt (t/in-days (t/interval (t/date-time 2016 1 1) (t/now))))

;; (take take-cnt (p/periodic-seq (t/date-time 2016 1 1) (t/days 1)))

;; (map copy-uri-to-file (take take-cnt (p/periodic-seq (t/date-time 2016 1 1) (t/days 1))))

;; (map copy-uri-to-file (take 7 (p/periodic-seq (t/date-time 2016 6 14) (t/days 1))))

;; (map copy-uri-to-file 
;;      (take 
;;       (t/in-days (t/interval (t/date-time 2015 1 1) (t/date-time 2015 12 31)))
;;            (p/periodic-seq (t/date-time 2015 1 1) (t/days 1))))


;; (map copy-uri-to-file 
;;      (take 
;;       (t/in-days (t/interval (t/date-time 2014 1 1) (t/date-time 2014 12 31)))
;;            (p/periodic-seq (t/date-time 2014 1 1) (t/days 1))))


;; (map copy-uri-to-file 
;;      (take 
;;       (t/in-days (t/interval (t/date-time 2013 1 1) (t/date-time 2013 12 31)))
;;            (p/periodic-seq (t/date-time 2013 1 1) (t/days 1))))
