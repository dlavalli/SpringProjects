package com.lavalliere.daniel.spring.nullspecify.controller;

// Can Annotate the entire package with JSpecify @NullMarked directly in the plugin section of pom.xml
// ie: eveything in this package is non-null by default so @Nullable is needed where allowed

import com.lavalliere.daniel.spring.nullspecify.domain.User;
import com.lavalliere.daniel.spring.nullspecify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@Slf4j
// @NullMarked // Can also NUllMarked the class directly
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    // private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/find-by-email")
    public User findUserByEmail(@RequestParam String email) {

        User user = userService.findUserByEmail(email);

        // Having the service declare @Nullable with @NullMarked on the package,
        // make it that IntelliJ will indicate the  possible NullPointerException when you hover over the firstName method
        if (user.firstName().equalsIgnoreCase("dan")) {
            log.info("Dan is here");
        }

        return user;
    }
}
