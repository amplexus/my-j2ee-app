package com.telstra.tdi.aspect;

import java.util.Stack;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.reflect.MethodSignature;

import com.telstra.tdi.annotation.Metric;

public aspect MetricAspect extends BaseAspect {

	pointcut metricMethods(Metric metricAnnotation) : 
    	@annotation(metricAnnotation);

	private static final Logger logger = Logger.getLogger(MetricAspect.class.getName());

	private int callLevel;
	private Stack<Long> startTimeStack = new Stack<Long>();

	before() : metricMethods(Metric) {
		callLevel++;
		startTimeStack.push(System.currentTimeMillis());
	}

	after() returning: metricMethods(Metric) {
		callLevel--;
		long duration = System.currentTimeMillis() - startTimeStack.pop();
		logMetrics(thisJoinPointStaticPart, duration, true, null);
	}

	after() throwing(Throwable t): metricMethods(Metric) {
		callLevel--;
		long duration = System.currentTimeMillis() - startTimeStack.pop();
		String exceptionName = t.getClass().getCanonicalName();
		logMetrics(thisJoinPointStaticPart, duration, false, exceptionName);
	}

	public void logMetrics(StaticPart joinPointStaticPart, long millis,
			boolean passed, String exceptionName) {
		final MethodSignature methodSignature = (MethodSignature) joinPointStaticPart
				.getSignature();
		final String methodSignatureStr = getSignatureWithoutNamespace(methodSignature);

		try {
			String className = getSimpleClassName(methodSignature);
			String methodName = getMethodName(methodSignature, true);
			long threadId = Thread.currentThread().getId();
			logger.info("," + (passed ? "PASSED" : "FAILED [" + exceptionName + "]")
					+ "," + className + "_" + methodName + ",duration,"
					+ millis + "," + threadId + "," + callLevel);
		} catch (RuntimeException e) {
			String message = String.format("Unexpected exception occurred during logging metric for method '%s'", methodSignatureStr);
			logger.error(message, e);
		}
	}
}
