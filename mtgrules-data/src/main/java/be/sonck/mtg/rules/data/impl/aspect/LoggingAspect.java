package be.sonck.mtg.rules.data.impl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by johansonck on 05/09/15.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final String POINTCUT = "execute()";

    private static final Map<Class, Logger> LOGGERS = new HashMap<>();


    @Pointcut("execution(* be.sonck.mtg.rules..*.*(..))")
    public void execute() {}

    @Before(POINTCUT)
    public void logBefore(JoinPoint joinPoint) {
        String message = toString(joinPoint);

        log(joinPoint, message);
    }

    @AfterReturning(value = POINTCUT, returning = "returnValue")
    public void logAfter(JoinPoint joinPoint, Object returnValue) {
        String message = toString(joinPoint) + " returned " + toString(returnValue);

        log(joinPoint, message);
    }

    private void log(JoinPoint joinPoint, String message) {
        getLogger(joinPoint).trace(message);
    }

    private Logger getLogger(JoinPoint joinPoint) {
        Class declaringType = joinPoint.getSignature().getDeclaringType();

        return getLogger(declaringType);
    }

    private Logger getLogger(Class clazz) {
        Logger logger = LOGGERS.get(clazz);

        if (logger == null) {
            logger = LoggerFactory.getLogger(clazz);
            LOGGERS.put(clazz, logger);
        }

        return logger;
    }

    private String toString(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();

        return signature.getName() + "(" + argsToString(joinPoint) + ")";
    }

    private String argsToString(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder();
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (builder.length() > 0) { builder.append(", "); }

            builder.append(arg == null ? null : toString(arg));
        }

        return builder.toString();
    }

    private String toString(Object object) {
        StringBuilder builder = new StringBuilder();

        if (object instanceof String) {
            builder.append("\"" + object + "\"");

        } else if (object instanceof Optional) {
            builder.append(object.toString());

        } else {
            builder.append(object.getClass().getSimpleName());
            builder.append("[");

            builder.append(object.toString());

            builder.append("]");
        }

        return builder.toString();
    }
}
