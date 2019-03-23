package io.lishman.purple.repository;

import io.lishman.purple.entity.Country;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {
}
