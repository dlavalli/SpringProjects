package com.lavalliere.daniel.spring.apiversioning.components;

import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import org.springframework.web.accept.ApiVersionParser;

@Component
@NullMarked  // By default all args ar NOT null
public class ApiVersionParsing implements ApiVersionParser<String> {
    @Override
    public String parseVersion(String version) {
        if (version.toLowerCase().startsWith("v")) {
            version = version.substring(1);
        }

        if (!version.contains(".")) {
            version = version + ".0";
        }

        return version;
    }
}
