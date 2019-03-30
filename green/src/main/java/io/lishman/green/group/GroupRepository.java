package io.lishman.green.group;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(final String name);

}
