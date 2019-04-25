package io.lishman.cyan.service;

import io.lishman.cyan.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class FeignPeopleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignPeopleService.class);

    private final FeignPeopleClient feignPeopleClient;

    public FeignPeopleService(final FeignPeopleClient feignPeopleClient) {
        this.feignPeopleClient = feignPeopleClient;
    }

    public List<Person> getPeople() {
        LOGGER.info("Get People with Feign");
        return feignPeopleClient.getPeople();
    }

    public Person getPerson(final Long id) {
        LOGGER.info("Get Person {} with Feign", id);
        return feignPeopleClient.getPerson(id);
    }
}
