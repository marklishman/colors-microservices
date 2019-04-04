package io.lishman.green.repository;

import io.lishman.green.entity.ItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByUuid(final UUID id);

    List<ItemEntity> findByCorrelationId(final UUID correlationId);

    List<ItemEntity> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);

}
