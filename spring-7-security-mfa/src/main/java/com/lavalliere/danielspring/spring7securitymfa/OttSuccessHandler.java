package com.lavalliere.danielspring.spring7securitymfa;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private final OneTimeTokenGenerationSuccessHandler successHandler =
              new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        OneTimeToken oneTimeToken
    ) throws IOException, ServletException {
        // Make the request work from anywhere instead of hardcoding the url + token
        String magicLink = ServletUriComponentsBuilder
            .fromCurrentContextPath()  // Based on the current context path (ie: based on environment we are in)
            .path("/login/ott")
            .queryParam("token", oneTimeToken.getTokenValue())
            .toUriString();

        // Now you would send the token any method you want
        // Email, SMS, Custom implementation, etc.
        // For this demo, we just print the token
        log.info("Magic link: {}", magicLink);


        successHandler.handle(request, response, oneTimeToken);
    }
}
