package com.frankmoley.lil.fid.config;

import com.frankmoley.lil.fid.service.GreetingService;
import com.frankmoley.lil.fid.service.OutputService;
import com.frankmoley.lil.fid.service.TimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;


@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties

//application.yaml / bootstrap.yaml
@ConfigurationProperties(prefix="yml")
@PropertySource(
    value = {
        "classpath:bootstrap.yml",
        "classpath:application.yml"
    },
    factory = YamlPropertySourceFactory.class
)

// application.properties / bootstrap.properties
// @ComponentScan(basePackages = "com.frankmoley.lil.fid")
// @PropertySource("classpath:application.properties")

@ComponentScan(basePackages = "com.frankmoley.lil.fid")
public class ApplicationConfig {

    /*
    // No longer needed with @ComponentScan which creates the beans

    @Value("${app.greeting}")
    private String greeting;

    @Value("${app.name}")
    private String name;

    @Bean
    public GreetingService greetingService() {
        return new GreetingService();
    }

    @Bean
    public TimeService timeService() {
        return new TimeService();
    }

    @Bean
    public OutputService outputService(
        GreetingService greetingService,
        TimeService timeService
    ) {
        return new OutputService(greetingService, timeService);
    }
     */
}
