package io.lishman.cyan.controller;

import io.lishman.cyan.model.Employee;
import io.lishman.cyan.model.EmployeeEvent;
import io.lishman.cyan.service.WebClientEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
class EmployeesController {

    final WebClientEmployeeService webClientEmployeeService;

    EmployeesController(WebClientEmployeeService webClientEmployeeService) {
        this.webClientEmployeeService = webClientEmployeeService;
    }

    @GetMapping
    ResponseEntity<Flux<Employee>> getEmployees() {
        return webClientEmployeeService.getEmployees();
    }

    @GetMapping("/{id}")
    ResponseEntity<Mono<Employee>> getEmployee(@PathVariable("id") final String id) {
        return webClientEmployeeService.getEmployee(id);
    }

    @GetMapping("webflux")
    ResponseEntity<String> webFluxClient() {
        webClientEmployeeService.webFluxClient();
        return ResponseEntity.ok("done");
    }

    @PostMapping("/events/toggle")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> toggleEventStream() {
        final String status = webClientEmployeeService.toggleEmployeeEventStream();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/events/latest")
    ResponseEntity<EmployeeEvent> latestEventStream() {
        final EmployeeEvent employeeEvent = webClientEmployeeService.getLatestEmployeeEvent();
        return ResponseEntity.ok(employeeEvent);
    }

}
