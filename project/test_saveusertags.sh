#!/bin/sh
FILTERLIST="$@"
#FILTERLIST="['one fish', 'two fish', 'red fish', 'blue fish']"
python -c "x = '''username=qwerty&filters=${FILTERLIST}'''; print('POST /save_user_tags HTTP/1.0\r\nContent-length: %d\r\n\r\n%s' % (len(x), x))" | netcat localhost 8000
