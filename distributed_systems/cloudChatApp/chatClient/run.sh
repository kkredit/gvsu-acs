#!/bin/bash
pushd target
java -jar gae-restlet-client-example-1.0-SNAPSHOT-jar-with-dependencies.jar $@
popd
