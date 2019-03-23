package io.lishman.purple.entity;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name", types = { Item.class })
public interface ItemNameProjection {
    String getName();
    String getDescription();
}
