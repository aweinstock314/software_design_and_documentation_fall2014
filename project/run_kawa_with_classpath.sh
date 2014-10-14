#!/bin/sh
export CLASSPATH="$(find dependencies/ -name '*.jar' | tr '\n' ':')":build/
rlwrap java kawa.repl -f repl_helpers.scm -s
