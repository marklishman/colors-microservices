package io.lishman.webflux;

import io.lishman.webflux.handler.EmployeeHandler;
import io.lishman.webflux.model.Employee;
import io.lishman.webflux.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class WhiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteApplication.class, args);
    }

    @Bean
    CommandLineRunner init(EmployeeRepository repo) {
        return args -> {
            Flux<Employee> employeeFlux = Flux.just(
                new Employee("one", "employee_one", "one@email.com", "012345672", "www.one.com"),
                new Employee("two", "employee_two", "two@email.com", "056543336", "www.two.com"),
                new Employee("three", "employee_three", "three@email.com", "048745663", "www.three.com"),
                new Employee("four", "employee_four", "four@email.com", "087645767", "www.four.com"),
                new Employee("five", "employee_five", "five@email.com", "087686324", "www.five.com"),
                new Employee("six", "employee_six", "six@email.com", "074657634", "www.six.com"),
                new Employee("seven", "employee_seven", "seven@email.com", "083647566", "www.seven.com"),
                new Employee("eight", "employee_eight", "eight@email.com", "073465763", "www.eight.com"),
                new Employee("nine", "employee_nine", "nine@email.com", "043756236", "www.nine.com")
            ).flatMap(repo::save);

            employeeFlux.thenMany(repo.findAll())
                    .subscribe(System.out::println);
        };
    }

    @Bean
    @Profile("!ControllerTest")
    RouterFunction<ServerResponse> routes(EmployeeHandler handler) {

        // ~~~~ Flat
        return route(GET("/handler/employees").and(accept(APPLICATION_JSON)), handler::getAllEmployees)
				.andRoute(POST("/handler/employees").and(contentType(APPLICATION_JSON)), handler::saveEmployee)
				.andRoute(DELETE("/handler/employees").and(accept(APPLICATION_JSON)), handler::deleteAllEmployees)
				.andRoute(GET("/handler/employees/events").and(accept(TEXT_EVENT_STREAM)), handler::getEmployeeEvents)
				.andRoute(GET("/handler/employees/{id}").and(accept(APPLICATION_JSON)), handler::getEmployee)
				.andRoute(PUT("/handler/employees/{id}").and(contentType(APPLICATION_JSON)), handler::updateEmployee)
				.andRoute(DELETE("/handler/employees/{id}").and(accept(APPLICATION_JSON)), handler::deleteEmployee);

        // ~~~~ Nested
//        return nest(path("/handler/employees"),
//                nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
//                        route(GET("/"), handler::getAllEmployees)
//                                .andRoute(method(HttpMethod.POST), handler::saveEmployee)
//                                .andRoute(DELETE("/"), handler::deleteAllEmployees)
//                                .andRoute(GET("/events"), handler::getEmployeeEvents)
//                                .andNest(path("/{id}"),
//                                        route(method(HttpMethod.GET), handler::getEmployee)
//                                                .andRoute(method(HttpMethod.PUT), handler::updateEmployee)
//                                                .andRoute(method(HttpMethod.DELETE), handler::deleteEmployee)
//                                )
//                )
//        );
    }


}
