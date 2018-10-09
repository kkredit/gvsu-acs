#!/bin/bash

PORT=1099
if [[ $# > 0 ]]; then
    PORT=$1
fi

ant run-presence-server -Dport=$PORT
