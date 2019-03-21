package io.lishman.purple.repository;

import io.lishman.purple.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findByUuid(final String uuid);

    List<Item> findByCorrelationId(final String correlationId, final Pageable pageable);

    List<Item> findByGroupName(final String name, final Pageable pageable);
}
