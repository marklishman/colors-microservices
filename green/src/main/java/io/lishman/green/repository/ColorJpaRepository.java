package io.lishman.green.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ColorJpaRepository extends JpaRepository<ColorEntity, String> {

    Optional<ColorEntity> findByColorName(final String colorName);

}
