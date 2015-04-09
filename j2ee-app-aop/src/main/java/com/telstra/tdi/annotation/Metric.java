package com.telstra.tdi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation for managing metrics.
 *
 * Attach this annotation to methods you want trackedd for performance.
 *
 * @author craig
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Metric {

	public static final int DEFAULT_THRESHOLD_MILLIS = 5000;

	long thresholdMillis() default DEFAULT_THRESHOLD_MILLIS;
}
