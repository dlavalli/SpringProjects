package com.frankmoley.lil.fid.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    // private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {
        // Do nothing
    }

    private void logMethodCall(
        String advice,
        JoinPoint joinPoint,
        String totalTime,
        String postfix
    ) {
        Object[] args = joinPoint.getArgs();
        String methodargs = "";
        if (args != null && args.length > 0) {
            methodargs = Arrays.asList(args).stream().map(Object::toString).collect(Collectors.joining(" | "));
        }
        String msg = STR."\{advice}: Method=\{joinPoint.getSignature().getName()} \{totalTime} | args=[ \{methodargs} ] \{postfix}";

        log.info(msg.replace("| ]", "]"));
    }

    @Before("executeLogging()")
    public void beforeLogMethodCall(JoinPoint joinPoint) {
        logMethodCall("Before", joinPoint, "", "");
    }


    @AfterReturning(
        pointcut = "executeLogging()",
        returning = "returnValue"
    )
    public void afterLogMethodCall(
        JoinPoint joinPoint,
        Object returnValue  // Must match the returning name in annotation
    ) {
        String postfix =  STR." returning: \{returnValue.toString()}";
        if (returnValue instanceof Collection<?> collection) {
            postfix = STR." returning \{collection.size()} instances";
        }
        logMethodCall("After", joinPoint, "", postfix);
    }

    @Around("executeLogging()")
    public Object aroundLogMethodCall(
        ProceedingJoinPoint joinPoint
    ) throws Throwable {
        Instant starTime = Instant.now();
        Thread.sleep(1);
        Object returnValue = joinPoint.proceed();
        Instant endTime = Instant.now();
        Duration durationBetween = Duration.between(starTime, endTime);
        String duration = STR." duration: \{durationBetween.toMillis()}ms ";
        String postfix =  STR." returning: \{returnValue.toString()}";
        if (returnValue instanceof Collection<?> collection) {
            postfix = STR." returning \{collection.size()} instances";
        }
        logMethodCall("Around", joinPoint, duration, postfix);
        return returnValue;
    }
}
