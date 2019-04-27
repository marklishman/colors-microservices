package io.lishman.webflux.contoller;

import io.lishman.webflux.model.Employee;
import io.lishman.webflux.model.EmployeeEvent;
import io.lishman.webflux.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/controller/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public Flux<Employee> getAllUsers() {
        return employeeRepository.findAll();
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<Employee>> getUser(@PathVariable("id") final String id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Employee> saveUser(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Employee>> updateUser(@PathVariable("id") String id, @RequestBody Employee employee) {
        return employeeRepository
                .findById(id)
                .flatMap(existingUser -> {
                    var updatedUser = new Employee(
                            id,
                            employee.getName(),
                            employee.getEmail(),
                            employee.getPhone(),
                            employee.getEmployeeName(),
                            employee.getWebsite()
                    );
                    return employeeRepository.save(updatedUser);
                })
                .map(updateUser -> ResponseEntity.ok(updateUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
        return employeeRepository.findById(id)
                .flatMap(existingUser ->
                        employeeRepository.delete(existingUser)
                            .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return employeeRepository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getUserEvents() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(val ->
                        new EmployeeEvent(val, "Employee Event " + val)
                );
    }

}
