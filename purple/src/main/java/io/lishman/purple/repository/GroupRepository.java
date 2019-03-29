package io.lishman.purple.repository;

import io.lishman.purple.entity.GroupEntity;
import io.lishman.purple.entity.projection.GroupNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "groups", collectionResourceRel = "groups", excerptProjection = GroupNameProjection.class)
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(final String name);

}
