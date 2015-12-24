# elmish

An experiment to see if the Elm Architecture can be emulated in ClojureScript.

## Run with Docker
If you would like to enable hot code reloading when the docker daemon is not running on your host then you will need to change the line `:figwheel true` to
`:figwheel {:websocket-host "dockerhost"}` in `project.clj`. This optional. 


```sh
docker run -it --name clojure-dev -v `pwd`:/code -p 3449:3449 clojure bash
cd /code
lein figwheel simple
```

Go to [dockerhost:3449](http://dockerhost:3449), where dockerhost is the
accessible host which is running the docker daemon and container run above.

If hot code reloading is working you can edit the files to see changes.

## Run normally

To get an interactive development environment run:

    lein figwheel simple

Open your browser at [localhost:3449](http://localhost:3449/).

