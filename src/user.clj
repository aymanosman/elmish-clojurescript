(ns cljs.user
  (:require
   [clojure.core.match :refer :all]
   ;; [cljs-http.client :as http]
   ))

(def foo
  (match
   [[:foo 2]]

   [[:foo x]] x
   :else :fail))

