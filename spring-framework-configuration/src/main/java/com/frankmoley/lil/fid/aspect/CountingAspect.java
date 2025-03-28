package com.frankmoley.lil.fid.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class CountingAspect {
    private static final Map<String, Integer> countingMap = new HashMap<>();

    @Pointcut("@annotation(Countable)")
    public void executeCounting() {
        // Do nothing
    }

    // DO NOT FORGET trailing () else won't run
    @Before("executeCounting()")
    public void incrementCount(JoinPoint joinPoint) {
        String methodName = STR."\{joinPoint.getSignature().getDeclaringType()}.\{joinPoint.getSignature().getName()}";
        if (countingMap.containsKey(methodName)) {
            countingMap.put(methodName, countingMap.get(methodName) + 1);
        } else {
            countingMap.put(methodName, 1);
        }

        String msg = countingMap.entrySet().stream()
            .map(entry -> STR."\{entry.getKey()}::\{entry.getValue()}")
            .collect(Collectors.joining(" | "));
        log.info(msg);
    }
}
