package io.lishman.purple.repository;

import io.lishman.purple.entity.PersonEntity;
import io.lishman.purple.entity.projection.PersonNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource (
        path = "people",
        collectionResourceRel = "people",
        collectionResourceDescription = @Description("A collection of people"),
        itemResourceRel = "person",
        itemResourceDescription = @Description("A single person"),
        excerptProjection = PersonNameProjection.class
)
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @RestResource(path = "findByLastName")
    List<PersonEntity> findByLastNameContainingIgnoreCase(final String nameContains);

}
