package guru.springframework.spring_6_di.controllers;

import guru.springframework.spring_6_di.services.EnvironmentService;
import org.springframework.stereotype.Controller;

@Controller
public class EnvironmentController {

    private final EnvironmentService environmentService;

    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    public String getEnvironment() {
        return STR."You are in \{environmentService.getEnv()} environment";
    }
}
