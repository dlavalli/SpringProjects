package com.lavalliere.daniel.spring;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnMissingBean(CustomStarter.class)
// @ConditionalOnClass(CustomStarter.class)
@EnableConfigurationProperties(CustomStarterProperties.class)
public class CustomStarterAutoConfiguration {

    //@Bean
    //public CustomStarter customStarter() {
    //    return new CustomStarter();
    //}

    @Bean
    public CustomStarter customStarter(CustomStarterProperties props) {
        return new CustomStarter(props.getName());
    }
}