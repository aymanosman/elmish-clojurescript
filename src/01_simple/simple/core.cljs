(ns ^:figwheel-always simple.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
   [simple.counter :as counter] 
   [cljs.core.async :as async :refer [chan put!]]
   [cljs.core.match :refer-macros [match]]
   [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Hello Simple Example!")

;; Model
(def init
  {:left (counter/init 0)
   :right (counter/init 18)})


(defn -update [action model]
  (match
   [action]

   [[:left act]]
   (do
     (println "hey mambo")
     (update-in model [:left] (counter/-update act (:left model))))

   [[:right act]]
   (do
     (println "mambo italiano")
     (update-in model [:left] (counter/-update act (:left model))))

   [_]
   (println "Fail")
   ))

;; View
(defn view
  [chan model]
  [:div
   ;; Problem: I'm creating a new channel, but not one that forwards to the old
   ;; channel. That means the `action-chan' below is not receiving the right
   ;; messages.
   (counter/view (async/map (fn [act] [:left act]) [chan]) (:left model))
   (counter/view (async/map (fn [act] [:right act]) [chan]) (:right model))
   ])

;; Plumbing

(defonce model-atom (atom init))
(defonce action-chan (chan))

(defn app [chan model-atom]
  (println "render")
  (view chan @model-atom))

(go-loop []
  (let [act (<! action-chan)
        new-model (-update act @model-atom)]
    (println "new model: " new-model)
    (reset! model-atom new-model)
    (recur)))

(reagent/render-component
 [app action-chan model-atom]
 (. js/document (getElementById "app")))
