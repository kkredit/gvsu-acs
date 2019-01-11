
# Background

This is another extension of the chat application developed in other projects for this same
Distributed Systems class. This one uses a Java RESTlet application deployed on Google App Engine as
the chat server backend. It uses the Objectify API for App Engine persistence.

The skeleton source code for this application was developed by Jonathan Engelsma
([jengelsma](https://github.com/jengelsma)). See
[original server source](https://github.com/jengelsma/gae-restlet-example) and [original client
source](https://github.com/jengelsma/gae-restlet-client-example).

## Notice

This project is not up to my usual code quality standard, as I was low on time near the end.

# Usage

To build and deploy the server locally, run `./localRun.sh`. To deploy to Google App Engine, use
`./webrun.sh` (though it won't work for you unless you create your own account and change the target
in `src/main/webapp/WEB-INF/appengine-web.xml`). `./clearDevServerStorage.sh` clears the local
persistent storage cache.

After the local dev or web server is deployed, navigate to the chat_client and run `./run.sh [username]
[server=http://localhost:8080]`.
