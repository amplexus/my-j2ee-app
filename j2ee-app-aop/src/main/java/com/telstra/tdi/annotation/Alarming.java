package com.telstra.tdi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation for managing alarming.
 *
 * Attach this to methods you want alarmed and list the exceptions you want alarmed.
 *
 * @author craig
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Alarming {
	Class<? extends Throwable> [] exceptions();
}
