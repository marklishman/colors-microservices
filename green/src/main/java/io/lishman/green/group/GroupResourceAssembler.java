package io.lishman.green.group;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

final class GroupResourceAssembler extends ResourceAssemblerSupport<Group, GroupResource> {

    private GroupResourceAssembler() {
        super(GroupController.class, GroupResource.class);
    }

    static GroupResourceAssembler getInstance() {
        return new GroupResourceAssembler();
    }

    @Override
    protected GroupResource instantiateResource(Group group) {
        return GroupResource.fromGroup(group);
    }

    @Override
    public GroupResource toResource(Group group) {
        return createResourceWithId(group.getId(), group);
    }
}
