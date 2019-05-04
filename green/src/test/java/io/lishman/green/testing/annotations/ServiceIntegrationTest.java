package io.lishman.green.testing.annotations;

import io.lishman.green.testing.config.RepositoryMocks;
import io.lishman.green.service.UserService;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringJUnitConfig(classes = {
        UserService.class,
        RepositoryMocks.class
})
public @interface ServiceIntegrationTest { }
