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

    public Optional<GreenDetails> findByCorrelationId(final String uuid) {
        return colorJpaRepository.findByCorrelationId(uuid)
                .map(this::fromEntity);
    }

    public GreenDetails saveDetails(GreenDetails greenDetails) {
        final ColorEntity colorEntity = this.colorJpaRepository.save(
                ColorEntity.fromDetails(greenDetails)
        );
        return fromEntity(colorEntity);
    }

    private GreenDetails fromEntity(final ColorEntity colorEntity) {
        return GreenDetails.newInstance(
                colorEntity.getId(),
                colorEntity.getUuid(),
                colorEntity.getCorrelationId(),
                colorEntity.getDetails()
        );
    }
}
