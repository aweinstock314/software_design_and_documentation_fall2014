.PHONY: all alljavastuff clean
SOURCENAMES=$$(find src/ -name '*.java')

all: alljavastuff

alljavastuff: bin/
	javac -d bin/ ${SOURCENAMES}

bin/:
	mkdir bin/

clean:
	rm -r bin/
