package io.lishman.purple.entity.projection;

import io.lishman.purple.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name", types = { GroupEntity.class })
public interface GroupNameProjection {

    @Value("#{target.name} (#{target.description})")
    String getDetails();
}
