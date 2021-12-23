package guru.springframework.sfgjms.config;

// For this test application, we are going be sending out a message periodically.
// And to do this, we are going to enable scheduling so we can send that out
// at a fixed rate. So, we need to do a little bit of configuration with Spring
// to get this set up.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/*
 This just sets up everything to enable that task scheduling.
 And then, the task pull to actually go out and execute that task
 of sending a message.
*/
@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfig {
    @Bean
    TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
