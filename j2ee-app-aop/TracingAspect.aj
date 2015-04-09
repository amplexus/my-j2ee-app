package com.telstra.tdi.aspect;

import org.apache.log4j.Logger;

public aspect TracingAspect {

	private static final Logger logger = Logger.getLogger(TracingAspect.class.getName());

	pointcut tracedCall():
        call(public * *.*(..));

    before(): tracedCall() {
    	logger.info("Entering: " + thisJoinPoint);
    }
    
	after() returning: tracedCall() {
		logger.info("Exiting: " + thisJoinPoint);
	}
	
	after() throwing(Throwable t): tracedCall() {
		logger.info("Aborting: " + thisJoinPoint + " - " + t.getClass().getName());
	}

}
