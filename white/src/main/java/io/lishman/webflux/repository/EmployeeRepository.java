package io.lishman.webflux.repository;

import io.lishman.webflux.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {

}
