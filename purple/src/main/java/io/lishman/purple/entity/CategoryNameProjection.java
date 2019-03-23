package io.lishman.purple.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name", types = { CategoryEntity.class })
public interface CategoryNameProjection {

    @Value("#{target.name} - #{target.description}")
    String getDetails();
}
