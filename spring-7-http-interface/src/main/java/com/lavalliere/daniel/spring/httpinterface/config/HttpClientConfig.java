package com.lavalliere.daniel.spring.httpinterface.config;

import com.lavalliere.daniel.spring.httpinterface.service.TodoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.web.service.registry.ImportHttpServices;

@EnableResilientMethods // Required to activate core @Retryable
@Configuration(proxyBeanMethods = false)  // determines whether Spring will wrap your configuration class in a CGLIB proxy to intercept direct method calls between @Bean methods.
                                          // When you set proxyBeanMethods = false, Spring skips the CGLIB proxy generation entirely. The configuration class is treated as a regular POJO,
                                          // operating in what Spring calls @Bean Lite Mode. If you call a @Bean method directly in this mode, it behaves like standard Java. It bypasses
                                          // the Spring lifecycle completely, executes the method code, and returns a brand-new object instance every single time.

@ImportHttpServices(types = TodoService.class) // New addition in Spring framework 7
public class HttpClientConfig {

    // Replacement for manually creating the bean instance in the main application...

}
