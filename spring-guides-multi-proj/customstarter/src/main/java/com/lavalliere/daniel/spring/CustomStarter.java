package com.lavalliere.daniel.spring;

import lombok.Getter;

// Base on:  https://medium.com/@AlexanderObregon/building-a-custom-spring-boot-starter-for-shared-logic-9be5699aff18
@Getter
public class CustomStarter {
    private final String name;
    public CustomStarter(String name) {
        this.name = name;
    }
}