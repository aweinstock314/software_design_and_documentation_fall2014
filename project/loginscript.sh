#!/bin/sh
echo 'GET /needsauth/foo HTTP/1.0#$Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=#$' | tr '#$' '\r\n' | netcat localhost 8000
