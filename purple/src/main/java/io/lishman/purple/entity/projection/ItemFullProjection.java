package io.lishman.purple.entity.projection;

import io.lishman.purple.entity.ItemEntity;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Projection(name = "full", types = { ItemEntity.class })
public interface ItemFullProjection {

    String getId();
    UUID getUuid();
    String getName();
    String getDescription();
    String getCorrelationId();
    Integer getStatus();
    LocalDateTime getCreatedAt();
    List<DataFullProjection> getData();

}
