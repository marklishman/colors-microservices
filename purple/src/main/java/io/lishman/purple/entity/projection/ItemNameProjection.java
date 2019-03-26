package io.lishman.purple.entity.projection;

import io.lishman.purple.entity.ItemEntity;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name", types = { ItemEntity.class })
public interface ItemNameProjection {
    String getName();
    String getDescription();
}
