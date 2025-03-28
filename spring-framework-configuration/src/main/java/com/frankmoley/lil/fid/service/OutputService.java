package com.frankmoley.lil.fid.service;

import com.frankmoley.lil.fid.aspect.Countable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// https://medium.com/@AlexanderObregon/enhancing-logging-with-log-and-slf4j-in-spring-boot-applications-f7e70c6e4cc7
@Slf4j
@Service
public class OutputService {
    // private final Logger logger = LoggerFactory.getLogger(OutputService.class);
    private final GreetingService greetingService;
    private final TimeService timeService;

    @Value("${app.name}")
    private String name;

    public OutputService(GreetingService greetingService, TimeService timeService){
        this.greetingService = greetingService;
        this.timeService = timeService;
    }


    @Countable
    public void generateOutput() throws InterruptedException {
        String output = timeService.getCurrentTime() + " " + greetingService.getGreeting(name);
        log.info(output);
    }

}