package io.lishman.purple.repository;

import io.lishman.purple.entity.GroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "groups", collectionResourceRel = "groups")
public interface GroupRepository extends CrudRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(final String name);

}
