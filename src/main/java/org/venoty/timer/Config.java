package org.venoty.timer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.venoty.timer.aspects.TimerAspect;

@Configuration
public class Config {
    @Bean
    public TimerAspect timerAspect(){return new TimerAspect();}
}
