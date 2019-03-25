package io.lishman.purple.repository;

import io.lishman.purple.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "people", collectionResourceRel = "people")
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    @RestResource(path = "findByName")
    List<PersonEntity> findByNameContainingIgnoreCase(final String nameContains);
}
