package io.lishman.green.repository;

import io.lishman.green.model.GreenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ColorRepository {

    private final ColorJpaRepository colorJpaRepository;

    @Autowired
    public ColorRepository(final ColorJpaRepository colorJpaRepository) {
        this.colorJpaRepository = colorJpaRepository;
    }

    public Optional<GreenDetails> findById(final Long id) {
        return this.colorJpaRepository.findById(id)
                .map(this::fromEntity);
    }

    public Optional<GreenDetails> findByColor(final String color, final String instance) {
        return colorJpaRepository.findByColorNameAndColorInstance(color, instance)
                .map(this::fromEntity);
    }

    public GreenDetails saveDetails(GreenDetails greenDetails) {
        final ColorEntity colorEntity = this.colorJpaRepository.save(
                ColorEntity.newInstance(greenDetails)
        );
        return fromEntity(colorEntity);
    }

    private GreenDetails fromEntity(final ColorEntity colorEntity) {
        return GreenDetails.newInstance(
                colorEntity.getId(),
                colorEntity.getCorrelationId(),
                colorEntity.getColorName(),
                colorEntity.getColorInstance(),
                colorEntity.getDetails()
        );
    }
}
