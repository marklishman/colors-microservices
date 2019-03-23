package io.lishman.purple.entity;

import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "totals", types = { Item.class })
public interface ItemTotalsProjection {
    String getName();
    String getDescription();
    BigDecimal getTotal();
}
