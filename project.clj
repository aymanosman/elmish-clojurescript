(defproject elmish "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
		 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [reagent "0.5.0"]
                 [cljs-http "0.1.37"]]

  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-figwheel "0.4.1"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src/effects"]

              :figwheel { :on-jsload "elmish.core/on-js-reload" }

              :compiler {:main elmish.core
                         :asset-path "js/compiled/out"
                         :output-to "resources/public/js/compiled/bundle.js"
                         :output-dir "resources/public/js/compiled/out"
                         :source-map-timestamp true }}

             {:id "simple"
              :source-paths ["src/01_simple"]

              :figwheel true
              ;; :figwheel {:websocket-host "dockerhost"}

              :compiler {:main simple.core
                         :asset-path "js/compiled/simple/out"
                         :output-to "resources/public/js/compiled/bundle.js"
                         :output-dir "resources/public/js/compiled/simple/out"
                         :source-map-timestamp true }}

             {:id "min"
              :source-paths ["src"]
              :compiler {:output-to "resources/public/js/compiled/bundle.js"
                         :main elmish.core
                         :optimizations :advanced
                         :pretty-print false}}]}

  :figwheel {:css-dirs ["resources/public/css"] ;; watch and update CSS
             })
