package io.lishman.green.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface GreenJpaRepository extends JpaRepository<GreenEntity, Long> {

    Optional<GreenEntity> findByCorrelationId(final String uuid);

}
