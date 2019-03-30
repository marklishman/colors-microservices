package io.lishman.purple.entity.projection;

import io.lishman.purple.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name", types = { PersonEntity.class })
public interface PersonNameProjection {

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

}
