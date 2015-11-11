(ns ^:figwheel-always elmish.core
    (:require-macros [cljs.core.async.macros :refer [go go-loop]])
    (:require
     [cljs.core.async :refer [chan put!]]
     [cljs.core.match :refer-macros [match]]
     [elmish.random-gif :as gif]
     [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "EEdits to this text should show up in your developer console.")

;; Model
(defonce model
  (atom
   {:left (gif/init "cats")
    :right (gif/init "dogs")}))


;; Update
(defn -update ;; prefix to avoid name collision with cljs.core
  [action model]
  (println "Action: " action)

  (case action
    :inc
    (update-in model [:count] inc)))


;; View
(defn view
  [chan model]
  [:div
   (gif/view chan (:left model)) ;; TODO: map chan :left
   (gif/view chan (:right model))
   ])



;; Plumbing

(defonce action-chan (chan))

(defn app [chan model]
  (println "render")
  (view chan @model))

(go-loop []
  (let [act (<! action-chan)
        new-model (-update act @model)]
    (println "new model: " new-model)
    (reset! model new-model)
    (recur)))

(reagent/render-component
 [app action-chan model]
 (. js/document (getElementById "app")))
