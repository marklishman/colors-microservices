package io.lishman.green.testing.config;

import io.lishman.green.repository.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class RepositoryMocks {

    @MockBean
    private UserRepository userRepository;
}
