package com.lavalliere.daniel.spring.apiversioning.config;

import com.lavalliere.daniel.spring.apiversioning.components.ApiVersionParsing;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Using Spring configuration approach for API versioning
 */

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiVersionParsing apiVersionParsing;

    /*
     * Different ways you can configure api versioning
     *      path-segment (overwrite other solutions so cannot mix it with other methods) : /v1/users
     *      request header
     *      query param
     *      media type param
     */
    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        // WebMvcConfigurer.super.configureApiVersioning(configurer);
        configurer
            .addSupportedVersions("1.0", "1.1", "2.0")
            .setDefaultVersion("1.0")
            .usePathSegment(1) // Initially using because using  /users/v1/do-something
            // .useRequestHeader("X-API-Version")
            // .useQueryParam("apiVersion")
            // .useMediaTypeParameter(MediaType.APPLICATION_JSON, "apiVersion")
            .setVersionParser(apiVersionParsing);

    }
}
