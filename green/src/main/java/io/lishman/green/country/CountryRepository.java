package io.lishman.green.country;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Optional<CountryEntity> findByCode(final String code);

    List<CountryEntity> findByNameContainingIgnoreCase(final String nameContains);


}
