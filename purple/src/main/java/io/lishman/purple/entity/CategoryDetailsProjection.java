package io.lishman.purple.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "details", types = { Category.class })
public interface CategoryDetailsProjection {

    @Value("#{target.name} - #{target.description}")
    String getDetails();
}
