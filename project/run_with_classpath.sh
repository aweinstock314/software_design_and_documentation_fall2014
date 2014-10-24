#!/bin/sh
export CLASSPATH="$(find dependencies/ -name '*.jar' | tr '\n' ':')":build/
$@
