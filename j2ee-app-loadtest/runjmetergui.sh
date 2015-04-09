#!/bin/bash

plan=${1:-createorder}
java -jar target/jmeter/lib/ApacheJMeter-2.8.jar -t src/test/jmeter/$plan.jmx
