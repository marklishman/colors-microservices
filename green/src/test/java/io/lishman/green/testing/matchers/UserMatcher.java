package io.lishman.green.testing.matchers;

import io.lishman.green.model.User;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

public class UserMatcher extends TypeSafeMatcher<User> {

    private final User actual;

    private UserMatcher(final User actual) {
        this.actual = actual;
    }

    @Override
    protected boolean matchesSafely(final User expected) {
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

    public static UserMatcher matchesUser(final User user) {
        return new UserMatcher(user);
    }
}
