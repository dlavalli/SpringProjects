package com.lavalliere.daniel.spring.nullspecify.domain;

import lombok.Getter;
import org.jspecify.annotations.Nullable;

@Getter
public class Coffee {

    private final String type;
    private final String size;

    @Nullable
    private final String customization;

    public Coffee(String type, String size, @Nullable String customization) {
        this.type = type;
        this.size = size;
        this.customization = customization;
    }

    public @Nullable String getCustomization() {
        return customization;
    }
}
