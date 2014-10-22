#!/bin/sh
USERNAME="username"
PASSWORD="password"
TOKEN="$(echo -n "${USERNAME}:${PASSWORD}" | base64)"
echo 'GET /needsauth/foo HTTP/1.0#$Authorization: Basic '${TOKEN}'#$' | tr '#$' '\r\n' | netcat localhost 8000
