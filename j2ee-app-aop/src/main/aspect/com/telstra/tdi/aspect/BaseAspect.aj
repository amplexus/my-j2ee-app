package com.telstra.tdi.aspect;

import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Base Aspect for all aspects
 *    
 * @author d642706
 *
 */
public abstract aspect BaseAspect {

    /**
     * Pointcut for all ObjectDumper methods.
     * <p>
     * This pointcut will be used to prevent calls from ObjectDumper being logged/timed/whatever.
     */
    pointcut objectDumperCalling() : execution(* com .telstra.sdfcore.csc.common.utils.ObjectDumper..*(..));

	public static String getServiceName(MethodSignature methodSignature) {
		String simpleClassName = getSimpleClassName(methodSignature);
		if (simpleClassName.endsWith("Impl")) {
			return simpleClassName.substring(0, simpleClassName.lastIndexOf("Impl"));
		}
		return simpleClassName;
	}
	
	public static String getSimpleClassName(MethodSignature methodSignature) {
		String classWithPackage = methodSignature.getDeclaringTypeName();
		int index = classWithPackage.lastIndexOf(".");
		String className = classWithPackage.substring(index + 1);
		return className;
	}
	public static String getMethodName(MethodSignature methodSignature, boolean bFirstLetterUpperCase) {
		String methodName = methodSignature.getMethod().getName();
		if (bFirstLetterUpperCase) {
			String firstChar = methodName.substring(0,1) ; 
			String firstCharUpperCase = firstChar.toUpperCase() ;
			methodName = firstCharUpperCase + methodName.substring(1) ;
		} 
		return methodName;
	}
	
	public static String getSignature(StaticPart joinPointStaticPart) {
		return joinPointStaticPart.getSignature().toString();
	}
	
    public static String getSignatureWithoutNamespace(MethodSignature methodSignature)
    {
        String signature = stripNamespace(methodSignature.getReturnType().toString())
                         + " "
                         + stripNamespace(methodSignature.getMethod().getName())
                         + "(";

        for (Class<?> parameter : methodSignature.getParameterTypes())
        {
            if (signature.endsWith("(") == false)
            {
                signature += ", ";
            }

            signature += stripNamespace(parameter.getName());
        }

        signature += ")";

        return signature;
    }

    private static String stripNamespace(String withNamespace)
    {
        String withoutNamespace = withNamespace;

        int i = withNamespace.lastIndexOf(".");
        if (i > 0)
        {
            withoutNamespace = withNamespace.substring(i + 1);
        }

        i = withoutNamespace.lastIndexOf(";");
        if (i > 0)
        {
            withoutNamespace = withoutNamespace.substring(0, i) + "[]";
        }

        return withoutNamespace;
    }
}
