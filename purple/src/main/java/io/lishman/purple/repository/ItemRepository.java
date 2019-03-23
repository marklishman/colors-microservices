package io.lishman.purple.repository;

import io.lishman.purple.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findByUuid(final UUID id);

    List<Item> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName")
    List<Item> findByGroupNameIgnoreCase(final String groupName, final Pageable pageable);
}
