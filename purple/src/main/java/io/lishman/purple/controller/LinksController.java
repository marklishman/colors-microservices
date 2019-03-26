package io.lishman.purple.controller;

import io.lishman.purple.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksController {

    private RepositoryEntityLinks entityLinks;

    @Autowired
    public LinksController(RepositoryEntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @GetMapping("links")
    public Link links() {
        return entityLinks.linkToSingleResource(PersonEntity.class, 3);
    }
}
