package com.lavalliere.danielspring.spring7securitymfa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.server.SecurityWebFilterChain;  // For async web

import java.time.Duration;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity // (debug=true)   // For dev only !!!!
// We want to enable Both One time token and password (ie:  MFA)
@EnableMultiFactorAuthentication(authorities = {
    FactorGrantedAuthority.PASSWORD_AUTHORITY,  // 1st is the password
    FactorGrantedAuthority.OTT_AUTHORITY        // 2nd the One-Time-Token
})
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/ott/sent").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
            )
            .formLogin(withDefaults())
            .oneTimeTokenLogin(withDefaults())  // Won't work with only this, as spring does not know how the token will be delivered
            // One way is to provide a OneTimeToken handler
            .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        var user = User
            .withUsername("user")
            .password("{noop}password")  // DO NOT DO THIS IN PROD, just for demo purpose
            .roles("USER")
            .build();

        var admin = User
            .withUsername("admin")
            .password("{noop}password")  // DO NOT DO THIS IN PROD, just for demo purpose
            .roles("ADMIN", "USER")
            .build();

        return new InMemoryUserDetailsManager(user, admin); // DO NOT DO THIS IN PROD, just for demo purpose
    }

    // NOTE: you can customize the OneTimeTokenService :
    // https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/authentication/ott/OneTimeTokenService.html
    // There are currently 2 implementations:  InMemoryOneTimeTokenService (default) and JdbcOneTimeTokenService
    // This is an example, replacing the UUID returned by default by a PIN

    // @Bean
    // public OneTimeTokenService oneTimeTokenService() {
    //     PinOneTimeTokenService service = new PinOneTimeTokenService();
    //     service.setTokenExpiresIn(Duration.ofMinutes(3));
    //     return service;
    // }
}