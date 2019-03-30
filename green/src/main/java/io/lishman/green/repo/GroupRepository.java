package io.lishman.green.repo;

import io.lishman.green.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GroupRepository {

    private final GroupJpaRepository groupJpaRepository;

    @Autowired
    public GroupRepository(final GroupJpaRepository groupJpaRepository) {
        this.groupJpaRepository = groupJpaRepository;
    }

    public Optional<Group> findById(final Long id) {
        return this.groupJpaRepository.findById(id)
                .map(this::fromEntity);
    }

    public Group saveDetails(Group group) {
        final GroupEntity groupEntity = this.groupJpaRepository.save(
                GroupEntity.fromGroup(group)
        );
        return fromEntity(groupEntity);
    }

    private Group fromEntity(final GroupEntity groupEntity) {
        return Group.newInstance(
                groupEntity.getId(),
                groupEntity.getName(),
                groupEntity.getDescription()
        );
    }
}
