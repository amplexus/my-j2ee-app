package com.telstra.tdi.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;
import com.telstra.tdi.annotation.Alarming;
//import com.telstra.services.AlarmHandler;

public aspect AlarmingAspect extends BaseAspect {
	
	pointcut alarmMethods(Alarming alarmAnnotation) : 
    	@annotation(alarmAnnotation);
	
	private static final Logger logger = Logger.getLogger(AlarmingAspect.class.getName());
	
	after(Alarming alarmAnnotation) throwing(Throwable ex): alarmMethods(alarmAnnotation) {
		final MethodSignature methodSignature = (MethodSignature)thisJoinPointStaticPart.getSignature();
		final String methodSignatureStr = getSignatureWithoutNamespace(methodSignature);
		
		try {			
			String alarmMethodName = getSimpleClassName(methodSignature) + "_" + getMethodName(methodSignature, true);		
			Throwable matchedCause = getMatchedCause(alarmAnnotation.exceptions(), ex);
			if (matchedCause != null) {
			//	AlarmHandler.handleServiceAlarming(thisJoinPoint.getThis(), alarmMethodName, matchedCause);
				logger.info("ALARM: " + alarmMethodName + " - " + matchedCause.getClass().getName());
			}
		} catch (RuntimeException e) {
			String message = String.format("Unexpected exception occurred during raising alarm for method '%s'", methodSignatureStr);
        	logger.error(message, e);
		}
	}

	/**
	 * For the actual exception, try to find if itself or it`s cause match the pre-defined alarm exception class set. 
	 * @param expectedExceptionClasses
	 * @param actualException
	 * @return
	 */
	private static Throwable getMatchedCause(Class<? extends Throwable>[] expectedExceptionClasses, Throwable actualException) {
		for (Class<? extends Throwable> expectedExceptionClass : expectedExceptionClasses) {
			Throwable cause = actualException; 
			while (cause != null) {
				if (expectedExceptionClass.isInstance(cause)) {			
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Found matched alarm exception cause: '%s' for the exception: '%s'", cause.getClass().getName(), actualException.getClass().getName()));
					}
					return cause;
				}
				cause = cause.getCause();
			}			
		}		
		return null;
	}
}
