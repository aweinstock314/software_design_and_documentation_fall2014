#!/bin/sh
rlwrap java -cp 'dependencies/kawa-1.14.jar:dependencies/postgresql-9.3-1102.jdbc4.jar:build/' kawa.repl -f repl_helpers.scm -s
