package io.lishman.purple.repository;

import io.lishman.purple.entity.Item;
import io.lishman.purple.entity.ItemNameProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(excerptProjection = ItemNameProjection.class)
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findByUuid(final UUID id);

    List<Item> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName")
    List<Item> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);
}
