#!/bin/sh
dropdb epic_db
createdb epic_db
psql epic_db < epic_db_schema.sql
cd project
export CLASSPATH="$(find dependencies/ -name '*.jar' | tr '\n' ':')":build/
ant
java edu.rpi.csci.sdd.epic.scraper.Scraper
java edu.rpi.csci.sdd.epic.webserver.WebServer
