(ns elmish.random-gif
  (:require
   [cljs.core.async :refer [put! chan]]
   [cljs.core.match :refer-macros [match]]))


(defn init [topic]
  {:topic topic
   :gif-url "images/waiting.gif"})

(def http-port (chan)) ;; TODO

(defn -update [action model]
  (match
   [action]

   [:request-more]
   (do
     (put! http-port :get-random-gif (:topic model))
     model)

   [[:new-gif maybe-url]]
   (if maybe-url
     (assoc model :gif-url maybe-url)
     model)))

(defn img-style [url]
  {"display" "inline-block"
   "width" "200px"
   "height" "200px"
   "background-image" (str "url(" url ")")})

(defn view [chan model]
  [:div {:style {"width" "200px"}}
   [:h2 (:topic model)]
   [:div {:style (img-style (:gif-url model))}]
   ])
