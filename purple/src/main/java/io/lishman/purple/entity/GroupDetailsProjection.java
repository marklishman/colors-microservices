package io.lishman.purple.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "details", types = { Group.class })
public interface GroupDetailsProjection {

    @Value("#{target.name} - #{target.description}")
    String getDetails();
}
