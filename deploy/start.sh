#!/bin/sh

rm -f tpid

nohup java -jar /var/www/lazyrest-0.0.1-SNAPSHOT.jar

echo $! > tpid

echo Start Success!
