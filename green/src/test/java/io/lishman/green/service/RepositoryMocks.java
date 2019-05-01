package io.lishman.green.service;

import io.lishman.green.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RepositoryMocks {

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}