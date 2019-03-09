package io.lishman.green.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ColorJpaRepository extends JpaRepository<ColorEntity, Long> {

    Optional<ColorEntity> findByCorrelationId(final String uuid);

}
