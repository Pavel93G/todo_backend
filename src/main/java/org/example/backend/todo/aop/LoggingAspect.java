package org.example.backend.todo.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log
public class LoggingAspect {
    @Around("execution(* org.example.backend.todo.controller..*(..)))")
    public Object profileControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        var className = methodSignature.getDeclaringType().getSimpleName();
        var methodName = methodSignature.getName();

        log.info("---------- Execution " + className + ", " + methodName + "  -----------");

        var stopWatch = new StopWatch();

        stopWatch.start();
        var proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();

        log.info("---------- Execution " + className + ", " + methodName + ", " + stopWatch.getTotalTimeMillis() +
                 "ms -----------");
        return proceed;
    }
}
