#!/bin/bash

extra=$@

function run {
    test="./main.rb -b$1 -d$2 -c$3 $extra"
    read -p "Press key to run '$test'..."
    eval $test
}

run 3 3 2
run 4 4 2
run 4 4 3
run 10 10 4
run 50 10 4
run 25 25 4