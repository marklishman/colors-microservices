package io.lishman.purple.repository;

import io.lishman.purple.entity.CountryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "countries", collectionResourceRel = "countries")
public interface CountryRepository extends PagingAndSortingRepository<CountryEntity, Long> {

    @RestResource(exported = false)
    Optional<CountryEntity> findByCode(final String code);

    @RestResource(path = "findByName")
    List<CountryEntity> findByNameContainingIgnoreCase(final String nameContains);
}
