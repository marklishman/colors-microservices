package io.lishman.purple.repository;

import io.lishman.purple.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource (
        path = "groups",
        collectionResourceRel = "groups",
        collectionResourceDescription = @Description("A collection of groups"),
        itemResourceRel = "group",
        itemResourceDescription = @Description("A single group")
)
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(final String name);

}
