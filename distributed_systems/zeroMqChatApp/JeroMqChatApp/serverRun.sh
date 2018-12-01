#!/bin/bash
mvn --quiet exec:java -Djava.rmi.server.useCodebaseOnly=false \
    -Djava.security.policy=policy \
    -Dexec.mainClass=server.PresenceServiceImpl \
    -Dexec.args="$(echo $@)"
