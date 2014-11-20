#!/bin/sh
CLASSES=$(find build -name '*Test.class' | sed 's#build/\|\.class##g' | sed 's#/#.#g')
./run_with_classpath.sh java org.junit.runner.JUnitCore ${CLASSES}
