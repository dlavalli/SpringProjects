package com.lavalliere.daniel.spring.scheduledtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTask {
    private int occurenceCount = 0;

    @Scheduled(fixedRate=1000)
    public void checkForMessage() {
        occurenceCount++;
        System.out.printf("Recuring task %d ...\n", occurenceCount);
    }
}
