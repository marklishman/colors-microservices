package io.lishman.green.repository;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.testing.fixtures.UserFixture;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static io.lishman.green.testing.matchers.UserEntityMatcher.matchesUserEntity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@Sql("/user-data.sql")
@DisplayName("User Repository Integration Tests")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void afterEach() {
        final String seq = jdbcTemplate.queryForObject(
                "SELECT sequence_name FROM information_schema.sequences LIMIT 1",
                String.class
        );

        jdbcTemplate.update("ALTER SEQUENCE green." + seq + " RESTART WITH 1");
    }

    @Test
    @DisplayName("Given a user row on the database, " +
            "when this row is selected , " +
            "then the table is identified by the entity mappings " +
            "and the properties are populated by the column mappings")
    void entityMapping() {
        final Long ID = UserFixture.chelseyDietrich().getId();
        final UserEntity actualUserEntity = repository.findById(ID).get();
        assertThat(actualUserEntity, is(equalTo(UserFixture.chelseyDietrichEntity())));
    }

    @Test
    @DisplayName("Given users already exists in the database, " +
            "when a new user is added, " +
            "then a row is inserted to the table, " +
            "and the id is the next in the sequence")
    void nextIdInSequence(@Autowired TestEntityManager entityManager) {

        final UserEntity insertedBobSmithEntity = repository.saveAndFlush(UserFixture.bobSmithEntity());

        assertThat(insertedBobSmithEntity, is(equalTo(UserFixture.bobSmithEntity())));
        final Long ID = UserFixture.bobSmith().getId();
        final UserEntity actualUserEntity = entityManager.find(UserEntity.class, ID);
        assertThat(actualUserEntity, is(equalTo(UserFixture.bobSmithEntity())));
    }

    @Test
    @DisplayName("Given a query method which finds users by surname, " +
            "when a search is performed, " +
            "then only the matching users are returned " +
            "and the search is case insensitive " +
            "and the results are ordered by descending surname")
    void findByLastName() {

        final List<UserEntity> userEntities = repository.findByLastNameContainingIgnoreCaseOrderByLastNameDesc("CH");

        assertThat(userEntities, hasSize(2));
        assertThat(userEntities.get(0).getLastName(), is(equalTo("Dietrich")));
        assertThat(userEntities.get(1).getLastName(), is(equalTo("Bauch")));
    }
}
