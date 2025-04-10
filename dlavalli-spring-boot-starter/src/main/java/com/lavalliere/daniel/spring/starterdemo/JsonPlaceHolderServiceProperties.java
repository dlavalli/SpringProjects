package com.lavalliere.daniel.spring.starterdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

// NOTE. record  here is NOT a requirement, just for this demo instead of class
@ConfigurationProperties("json-placeholder-service")
public record JsonPlaceHolderServiceProperties(
    @DefaultValue("http://localhost")
    String baseUrl
) { }
