package io.lishman.green.person;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<PersonEntity> findByLastNameContainingIgnoreCase(final String nameContains);

}
