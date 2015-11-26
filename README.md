# elmish

An experiment to see if the Elm Architecture can be emulated in ClojureScript.

## Run with Docker
You will need to change the line `:figwheel true` to
`:figwheel {:websocket-host "dockerhost"}` in `project.clj`
```sh
docker run -it --name clojure-dev -v `pwd`:/code -p 3449:3449 clojure bash
cd /code
lein figwheel simple
```

Go to [dockerhost:3449](http://dockerhost:3449), where dockerhost is the
accessible host which is running the docker daemon and container run above.

## Run normally

To get an interactive development environment run:

    lein figwheel simple

Open your browser at [localhost:3449](http://localhost:3449/).

