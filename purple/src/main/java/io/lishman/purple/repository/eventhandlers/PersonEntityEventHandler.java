package io.lishman.purple.repository.eventhandlers;

import io.lishman.purple.entity.PersonEntity;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(PersonEntity.class)
public class PersonEntityEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(PersonEntity p) {
        System.out.println(p);
    }

    @HandleAfterCreate
    public void handleAfterCreate(PersonEntity p) {
        System.out.println(p);
    }
}
