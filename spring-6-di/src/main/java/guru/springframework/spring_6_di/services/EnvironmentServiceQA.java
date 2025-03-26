package guru.springframework.spring_6_di.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("qa")
public class EnvironmentServiceQA implements EnvironmentService {
    @Override
    public String getEnv() {
        return "qa";
    }
}