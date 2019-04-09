package io.lishman.purple.repository;

import io.lishman.purple.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource (
        path = "countries",
        collectionResourceRel = "countries",
        collectionResourceDescription = @Description("A collection of countries"),
        itemResourceRel = "country",
        itemResourceDescription = @Description("A single country")
)
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    @RestResource(path = "findByName")
    List<CountryEntity> findByNameContainingIgnoreCase(final String nameContains);

    @RestResource(exported = false)
    Optional<CountryEntity> findByCode(final String code);

    @RestResource(exported = false)
    @Override
    void deleteById(Long id);

    @RestResource(exported = false)
    @Override
    void delete(CountryEntity countryEntity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends CountryEntity> var1);

}
