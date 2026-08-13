package com.lavalliere.danielspring.spring7openntelemetry.ot;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
// @RequiredArgsConstructor
@RestController
public class HomeController {

//    private final ObservationRegistry observationRegistry;

    @GetMapping("/")
    @Observed(name = "home.count")   // To add custom metrics on top of the ones that Spring adds for you
    public String home() {

        // Observing something, same as below but with a bit more flexibility
        // possibly tracking key/value pairs,
        // BUT it can pollute your code if you need this in multiple places
        //     so better instead to use the @Observed annotation
//        Observation.createNotStarted("home.counter", observationRegistry).observe(() -> {
//            log.info("Home endpoint called");
//            return "Hello World!";
//        });

        log.info("Home endpoint called");
        return "Hello World!";
    }

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        log.info("Greeting user: {}", name);
        simulateWork();
        return "Hello, " + name + "!";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        log.info("Starting slow operation");
        Thread.sleep(500);
        log.info("Slow operation completed");
        return "Done!";
    }

    private void simulateWork() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
