package io.lishman.purple.repository;

import io.lishman.purple.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByName(final String name);

}
