#!/bin/bash
SEQ="78
3
32
2
32
7
"
printf "$SEQ" | rlwrap sqlplus kreditk@orcl/ORACLE_PW @PLh10.sql
