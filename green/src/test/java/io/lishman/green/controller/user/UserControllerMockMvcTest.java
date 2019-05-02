package io.lishman.green.controller.user;

import io.lishman.green.controller.ServiceMocks;
import io.lishman.green.model.User;
import io.lishman.green.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(ServiceMocks.class)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Test
    public void testExample() throws Exception {
        final List<User> users = Collections.singletonList(user());
        given(userService.getAllUsers()).willReturn(users);

        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(containsString("user.me")));
    }

    private User user() {
        return User.newInstance(
                123L,
                "Bob",
                "Smith",
                "user.me",
                "abc@email.com",
                "01772 776453",
                25,
                "www.example.com"
        );
    }

}
