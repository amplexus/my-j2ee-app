#!/bin/bash

curl -s --header "content-type: text/xml;charset=utf-8" -d '@createreq.xml' http://localhost:7001/myjbossapp | xmllint --format -
