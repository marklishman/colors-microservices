package io.lishman.green.testing.matchers;

import io.lishman.green.entity.UserEntity;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

public class UserEntityMatcher extends TypeSafeMatcher<UserEntity> {

    private final UserEntity actual;

    private UserEntityMatcher(final UserEntity actual) {
        this.actual = actual;
    }

    @Override
    protected boolean matchesSafely(final UserEntity expected) {
        return Objects.equals(actual.getId(), expected.getId()) &&
                Objects.equals(actual.getFirstName(), expected.getFirstName()) &&
                Objects.equals(actual.getLastName(), expected.getLastName()) &&
                Objects.equals(actual.getUserName(), expected.getUserName()) &&
                Objects.equals(actual.getEmail(), expected.getEmail()) &&
                Objects.equals(actual.getPhoneNumber(), expected.getPhoneNumber()) &&
                Objects.equals(actual.getAge(), expected.getAge()) &&
                Objects.equals(actual.getWebsite(), expected.getWebsite());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(actual.toString());
    }

    public static UserEntityMatcher matchesUserEntity(final UserEntity user) {
        return new UserEntityMatcher(user);
    }
}
