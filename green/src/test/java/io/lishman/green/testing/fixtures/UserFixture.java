package io.lishman.green.testing.fixtures;

import io.lishman.green.model.User;

public class UserFixture {

    private UserFixture() {
    }

    public static User leanneGraham() {
        return User.newInstance(
                16L,
                "Leanne",
                "Graham",
                "Bret",
                "Sincere@april.biz",
                "1-770-736-8031 x56442",
                17,
                "hildegard.org"
        );
    }

    public static User nicholasRunolfsdottir() {
        return User.newInstance(
                23L,
                "Nicholas",
                "Runolfsdottir V",
                "Maxime_Nienow",
                "Sherwood@rosamond.me",
                "586.493.6943 x140",
                43,
                "jacynthe.com"
        );
    }
}
