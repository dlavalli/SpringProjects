package payroll;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



// In Spring’s approach to building RESTful web services, HTTP requests
// are handled by a controller. These components are identified by
// the @RestController  annotation

@RestController
public class EmployeeController {

    // Encapsulate all the known employees (ie: a proxy for the DB)
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // @GetMapping annotation maps HTTP GET requests onto specific handler methods.
    // It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    // @PostMapping annotation maps HTTP POST requests onto specific handler methods.
    // It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.POST).
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item
    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {
        // JpaRepository provides the orElseThrow
        // The orElseThrows() is a method (Provided bu java.util.Optional) call chained onto the Optional return type.
        // This construct is null safe and frees us from having to write (ugly?) null checking code.
        // If there is a null response, this method will throw a built-in Java exception
        // (ie:client receive HTTP error 404 through EmployeeNotFoundAdvice which intercepts the exception)
        // with a message indicating that the requested resource was not found.
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    // @PutMapping annotation maps HTTP PUT requests onto specific handler methods.
    // It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.PUT).

    // Simply put, the @RequestBody annotation maps the HttpRequest body to a transfer or domain object,
    // enabling automatic deserialization of the inbound HttpRequest body onto a Java object

    // Simply put, the @PathVariable annotation can be used to handle template variables in
    // the request URI mapping,  and use them as method parameters.
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(
            @RequestBody Employee newEmployee,
            @PathVariable Long id) {

        return repository.findById(id)  // Get an Optional Stream
                // For each employee we receive from the request, create a new object
                // and return it else return emloyee with only id info
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                // Return a new employee with only an ID
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    // @DeleteMapping annotation maps HTTP DELETE requests onto specific handler methods.
    // It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.DELETE).
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
