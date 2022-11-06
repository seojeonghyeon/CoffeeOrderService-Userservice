package com.zayden.coffeeorderserviceuserservice.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/*
 * Controller All Package Aspect
 * Before Logging and After Logging
 */

@Slf4j
@Component
@Aspect
public class ControllerPerfAspect {

    @Before("execution(* com.zayden.coffeeorderserviceuserservice.api.*.*(..)))")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        message("Before", method.getName());
    }

    @After("execution(* com.zayden.coffeeorderserviceuserservice.api.*.*(..)))")
    public void after(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        message("After", method.getName());
    }

    private void message(String pointcut, String methodName){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(pointcut);
        stringBuilder.append(" the ");
        stringBuilder.append(methodName);
        stringBuilder.append(" Method Service");

        log.info(stringBuilder.toString());
    }
}
