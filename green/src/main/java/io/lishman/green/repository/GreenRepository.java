package io.lishman.green.repository;

import io.lishman.green.model.GreenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GreenRepository {

    private final GreenJpaRepository greenJpaRepository;

    @Autowired
    public GreenRepository(final GreenJpaRepository greenJpaRepository) {
        this.greenJpaRepository = greenJpaRepository;
    }

    public Optional<GreenDetails> findById(final Long id) {
        return this.greenJpaRepository.findById(id)
                .map(this::fromEntity);
    }

    public Optional<GreenDetails> findByCorrelationId(final String uuid) {
        return greenJpaRepository.findByCorrelationId(uuid)
                .map(this::fromEntity);
    }

    public GreenDetails saveDetails(GreenDetails greenDetails) {
        final GreenEntity greenEntity = this.greenJpaRepository.save(
                GreenEntity.fromDetails(greenDetails)
        );
        return fromEntity(greenEntity);
    }

    private GreenDetails fromEntity(final GreenEntity greenEntity) {
        return GreenDetails.newInstance(
                greenEntity.getId(),
                greenEntity.getUuid(),
                greenEntity.getCorrelationId(),
                greenEntity.getDetails()
        );
    }
}
