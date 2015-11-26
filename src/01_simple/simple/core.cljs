(ns ^:figwheel-always simple.core
  "This demonstrates that it is possible to copy the simple Elm Architecture in
  ClojureScript"
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
   [simple.counter :as counter]
   [cljs.core.async :as async :refer [chan put!]]
   [cljs.core.match :refer-macros [match]]
   [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Hello Simple Example!")

;; Helper
;; A Question remains: should 'forward-to' remain as flexible as it is by
;; allowing the user to apply ANY function over the stream of values, or should
;; this be restricted to accepting a keyword to only support the intended use
;; case of 'wrapping' a nested action so that it can be identified later on?
(defn forward-to [to f]
  "Returns a new channel which will have its values piped to the channel
provided, applying the function f to each value"
  (let [from (chan)]
    (async/pipe (async/map f [from]) to)
    from))

;; Model
(def init
  {:left (counter/init 0)
   :right (counter/init 18)})


(defn -update [action {:keys [left right] :as model}]
  ;; (println action)

  (match
   [action]

   [[:left act]]
   (assoc model :left (counter/-update act left))

   [[:right act]]
   (assoc model :right (counter/-update act right))

   [_]
   (println "Failed pattern match")))

;; View
(defn view
  [chan {:keys [left right]}]
  [:div
   (counter/view (forward-to chan #(vector :left %)) left)
   (counter/view (forward-to chan #(vector :right %)) right)
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
