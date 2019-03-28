package io.lishman.purple.repository;

import io.lishman.purple.entity.ItemEntity;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(ItemEntity.class)
public class ItemEntityEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }

    @HandleAfterCreate
    public void handleAfterCreate(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }

    @HandleBeforeSave
    public void handleBeforeSave(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }

    @HandleAfterSave
    public void handleAfterSave(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }
}
