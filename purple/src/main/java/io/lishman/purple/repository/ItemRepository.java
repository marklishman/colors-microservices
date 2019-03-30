package io.lishman.purple.repository;

import io.lishman.purple.entity.ItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource (
        path = "items",
        collectionResourceRel = "items",
        collectionResourceDescription = @Description("A collection of items"),
        itemResourceRel = "item",
        itemResourceDescription = @Description("A single item")
)
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByUuid(final UUID id);

    List<ItemEntity> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName", rel = "findByGroupName")
    List<ItemEntity> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);

}
