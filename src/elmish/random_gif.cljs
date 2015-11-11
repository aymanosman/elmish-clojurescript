(ns elmish.random-gif
  (:require
   [cljs.core.async :refer [put! chan]]
   [cljs.core.match :refer-macros [match]]
   [cljs-http.client :as http]
   [clojure.core.typed.check.get :as http]))


(defn init [topic]
  {:topic topic
   :gif-url "images/waiting.gif"})

(def http-port (chan)) ;; TODO

(defn -update [action model]
  (match
   [action]

   [:request-more]
   (do
     (put! http-port (:topic model))
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

(defn view [chan {:keys [model gif-url]}]
  [:div {:style {"width" "200px"}}
   [:h2 topic]
   [:div {:style (img-style gif-url)}]
   [:button {:on-click #(put! chan :request-more)}]
   ])

(go-loop []
  (let [topic (<! http-port)
        response (<! (http/get "http://api.giphy.com/v1/gifs/random"
                               {:query-params {"api_key" "dc6zaTOxFJmzC"
                                               "tag" topic}}))]

    ))