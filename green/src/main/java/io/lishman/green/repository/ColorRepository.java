package io.lishman.green.repository;

import io.lishman.green.model.GreenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ColorRepository {

    private final ColorJpaRepository colorJpaRepository;

    @Autowired
    public ColorRepository(ColorJpaRepository colorJpaRepository) {
        this.colorJpaRepository = colorJpaRepository;
    }

    public Optional<GreenDetails> findByColorName(final String colorName) {

        return colorJpaRepository.findByColorName(colorName)
                .map(ColorRepository::fromEntity);
    }

    private static GreenDetails fromEntity(final ColorEntity colorEntity) {
        return GreenDetails.newInstance(
                colorEntity.getId(),
                colorEntity.getCorrelationId(),
                colorEntity.getColorName(),
                colorEntity.getColorInstance(),
                colorEntity.getDetails()
        );
    }

}
