#!/bin/bash
mvn --quiet exec:java -Djava.rmi.server.useCodebaseOnly=false \
    -Djava.security.policy=policy \
    -Dexec.mainClass=client.ChatClient \
    -Dexec.args="$(echo $@)"
