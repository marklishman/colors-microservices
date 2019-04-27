package io.lishman.webflux.repository;

import io.lishman.webflux.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {

    Flux<Employee> findByEmployeeName(String employeeName);

}
