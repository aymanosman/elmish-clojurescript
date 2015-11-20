(ns simple.counter
  (:require
   [cljs.core.async :refer [put!]]))

;; Model
(defn init [num]
  {:count num})

;; Update
(defn -update ;; prefix to avoid name collision with cljs.core
  [action model]
  ;; (println action)

  (case action
    :inc
    (update-in model [:count] inc)))


;; View
(defn view
  [chan model]
  [:div
   [:p (str "You clicked on me " (:count model) " times")]
   [:button {:on-click #(put! chan :inc)}
    "Click me"]])
