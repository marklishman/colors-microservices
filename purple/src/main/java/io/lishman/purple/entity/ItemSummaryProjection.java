package io.lishman.purple.entity;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "summary", types = { Item.class })
public interface ItemSummaryProjection {
    String getName();
    String getDescription();
}
