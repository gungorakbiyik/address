package com.gun.address.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.gun.address.repositories..*(..))")
    public Object logAllRepositories(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return log(proceedingJoinPoint);
    }

    @Pointcut("@annotation(Loggable)")
    public void logMethod() {}

    @Pointcut("@within(Loggable)")
    public void logClass() {}

    @Around("logMethod()")
    public Object logMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return log(proceedingJoinPoint);
    }

    @Around("logClass()")
    public Object logClass(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return log(proceedingJoinPoint);
    }

    private Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final StopWatch sw = new StopWatch();
        sw.start();
        Object retVal = proceedingJoinPoint.proceed();
        sw.stop();

        StringBuilder sb = new StringBuilder();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        if (proceedingJoinPoint.getArgs() != null && proceedingJoinPoint.getArgs().length > 0) {
            sb.append("[");
            Object[] args = proceedingJoinPoint.getArgs();
            String[] parameterNames = methodSignature.getParameterNames();
            for (int i = 0; i < args.length; i++) {
                if (parameterNames != null) {
                    sb.append(parameterNames[i])
                            .append("=");
                }
                sb.append(args[i]);
                if (i < args.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        } else {
            sb.append(" - ");
        }

        LOGGER.info("{}.{} - Execution Time: {} ms., args: {}, return value: {}",
                methodSignature.getDeclaringType().getSimpleName(),
                methodSignature.getName(),
                sw.getTotalTimeMillis(),
                sb.toString(),
                retVal
        );
        return retVal;
    }

}
