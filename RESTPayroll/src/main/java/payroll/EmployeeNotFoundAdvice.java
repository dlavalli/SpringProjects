package payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ControllerAdvice is a specialization of the @Component annotation
// which allows to handle exceptions across the whole application in one
// global handling component. It can be viewed as an interceptor of exceptions
// thrown by methods annotated with @RequestMapping and similar.

@ControllerAdvice
public class EmployeeNotFoundAdvice {

    // Simply put, the @RequestBody annotation maps the HttpRequest body to a transfer
    // or domain object, enabling automatic deserialization of the inbound HttpRequest body onto a Java object.

    // The @ExceptionHandler is an annotation used to handle the specific exceptions
    // and sending the custom responses to the client.

    // If we want to specify the response status of a controller method, we can mark that method with @ResponseStatus.
    // It has two interchangeable arguments for the desired response status: code, and value.

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }
}
