package io.lishman.green.controller;

import io.lishman.green.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ServiceMocks {

    @MockBean
    private UserService userService;
}
