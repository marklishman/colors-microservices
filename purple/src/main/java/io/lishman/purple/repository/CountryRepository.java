package io.lishman.purple.repository;

import io.lishman.purple.entity.Country;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {

    @RestResource(path = "findByName")
    List<Country> findByNameContainingIgnoreCase(final String nameContains);
}
