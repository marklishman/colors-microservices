package io.lishman.purple.controller;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class VersonController {

    @GetMapping("/version")
    public ResponseEntity<?> stats()  {
        final Resource<String> version = new Resource<>("1.2.3");
        version.add(linkTo(methodOn(VersonController.class).stats()).withSelfRel());
        return ResponseEntity.ok(version);
    }
}
