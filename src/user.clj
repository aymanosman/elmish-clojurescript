(ns user
  (:require [clojure.core.match :refer :all]))

(def foo
  (match
   [[:foo 2]]

   [[:foo x]] x
   :else :fail))

