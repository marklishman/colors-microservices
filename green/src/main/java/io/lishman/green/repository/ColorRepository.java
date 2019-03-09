package io.lishman.green.repository;

import io.lishman.green.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorEntity, String> {

    Optional<ColorEntity> findByColorName(final String colorName);


}
