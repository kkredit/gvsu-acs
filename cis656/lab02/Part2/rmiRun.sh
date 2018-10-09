#!/bin/bash

PORT=1099
if [[ $# > 0 ]]; then
    PORT=$1
fi

ant run-rmiregistry -Dport=$PORT
