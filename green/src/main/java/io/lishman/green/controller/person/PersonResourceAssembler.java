package io.lishman.green.controller.person;

import io.lishman.green.model.Person;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

final class PersonResourceAssembler extends ResourceAssemblerSupport<Person, PersonResource> {

    private PersonResourceAssembler() {
        super(PersonController.class, PersonResource.class);
    }

    public static PersonResourceAssembler getInstance() {
        return new PersonResourceAssembler();
    }

    @Override
    protected PersonResource instantiateResource(Person person) {
        return PersonResource.fromPerson(person);
    }

    @Override
    public PersonResource toResource(Person person) {
        return createResourceWithId(person.getId(), person);
    }
}

