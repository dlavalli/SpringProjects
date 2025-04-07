package com.lavalliere.daniel.shell.demo.components;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

// Could also use the @SpringBootApplication or an instance of @Configuration
// to annotata a method returning PromptProvider based on some configuration
@Component
public class MyPromptProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        // Notice that for % , %% is needed since treated as a special character
        return new AttributedString("%%# ");
    }
}
