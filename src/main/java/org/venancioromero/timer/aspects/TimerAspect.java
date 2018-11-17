package org.venancioromero.timer.aspects;

import org.springframework.beans.factory.annotation.Value;
import org.venancioromero.timer.annotations.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class TimerAspect {

    private File file;
    private BufferedWriter bw;
    private FileWriter fw;

    @Value("${timer.file.enable:false}")
    private boolean isFileEnabled;

    @Value("${timer.file.name:timer.log}")
    private String filename;

    @Value("${timer.file.csv.separator:;}")
    private String separator;

    private final Logger LOGGER = LoggerFactory.getLogger(TimerAspect.class);

    @Around(value = "@annotation(org.venancioromero.timer.annotations.Timer)")
    public Object timerMethods(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Timer timer   = method.getAnnotation(Timer.class);

        long start    = System.nanoTime();
        Object retval = pjp.proceed();

        long time     = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        String clazz  = pjp.getTarget().getClass().toString();


        String msg    = "Time:" + time + " ms || " + clazz + " || Method: " + method.getName();

        if (time > timer.error())
            LOGGER.error(msg);
        else if (time > timer.warn())
            LOGGER.warn(msg);
        else
            LOGGER.info(msg);

        writeFile(time,method.getName(),clazz);

        return retval;
    }
    private void writeFile(long time, String method,String clazz) throws IOException {

        if (!isFileEnabled)
            return;

        bw.write(System.currentTimeMillis() + separator +
                                           time + separator +
                                         method + separator +
                                         clazz  +"\n");

    }

    @PostConstruct
    private void init() throws Exception{

        String header = "";

        file = new File(filename);

        if(!file.exists()) {
            file.createNewFile();
            header = "timestamp" + separator +
                     "time"      + separator +
                     "method"    + separator +
                     "class\n";
        }

        fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);

        fw.write(header);
        bw.flush();
    }

    @PreDestroy
    private void finish() throws Exception{
        try {
            if (bw != null) bw.close();
            if (fw != null) fw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
