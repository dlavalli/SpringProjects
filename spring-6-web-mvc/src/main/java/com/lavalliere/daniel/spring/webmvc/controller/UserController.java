package com.lavalliere.daniel.spring.webmvc.controller;

import com.lavalliere.daniel.spring.webmvc.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    /*
        A RestController in Spring MVC does not strictly require the use of ResponseEntity as a return type. While ResponseEntity offers fine-grained control over the HTTP response, including status codes and headers, methods within a RestController can return other types as well.
        If a method in a RestController returns a simple object (e.g., a custom class, a String, or a collection), Spring MVC automatically handles the serialization of that object into the response body (typically as JSON or XML) and sets the HTTP status code to 200 OK by default.
        ResponseEntity becomes necessary when there's a need to customize the HTTP response beyond the default behavior. This includes scenarios such as:

        Setting a specific status code other than 200 OK (e.g., 201 Created, 404 Not Found, 500 Internal Server Error).
        Adding custom headers to the response.
        Returning a response body with no content (e.g., in a DELETE operation).

        See also:
        https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/jackson.html
     */
    @GetMapping("hello")
    public User getUser() {
        User user = new User(1234, "Bryan");
        return user;
    }

}
