package io.lishman.green.controller.user;

import io.lishman.green.model.User;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

final class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    private UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    public static UserResourceAssembler getInstance() {
        return new UserResourceAssembler();
    }

    @Override
    protected UserResource instantiateResource(final User user) {
        return UserResource.fromUser(user);
    }

    @Override
    public UserResource toResource(final User user) {
        return createResourceWithId(user.getId(), user);
    }
}

