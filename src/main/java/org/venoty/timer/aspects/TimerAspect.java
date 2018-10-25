package org.venoty.timer.aspects;

import org.venoty.timer.annotations.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class TimerAspect {

    private Logger logger = LoggerFactory.getLogger(TimerAspect.class);


    @Around(value = "@annotation(org.venoty.timer.annotations.Timer)")
    public Object timerMethods(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Timer timer   = method.getAnnotation(Timer.class);

        long start    = System.nanoTime();
        Object retval = pjp.proceed();
        long time     = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        String msg    = "Time:" + time + " ms || " + pjp.getTarget().getClass() + " || Method: " + method.getName();

        if (time > timer.error())
            logger.error(msg);
        else if (time > timer.warn())
            logger.warn(msg);
        else
            logger.info(msg);

        return retval;
    }
}
