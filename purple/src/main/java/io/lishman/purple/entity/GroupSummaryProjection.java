package io.lishman.purple.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "summary", types = { Group.class })
public interface GroupSummaryProjection {

    @Value("#{target.name} - #{target.description}")
    String getDetails();
}
