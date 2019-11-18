package jp.co.musako.application.contoller;

import jp.co.musako.domain.model.User;
import jp.co.musako.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users/{id}")
    ResponseEntity<EntityModel<User>> findOne(@PathVariable Long id) throws IllegalArgumentException {

        return userService.findById(id)
                .map(user -> new EntityModel<>(user,
                        linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel()
                                .andAffordance(afford(methodOn(UserController.class).updateUser(null, user.getId())))
                                .andAffordance(afford(methodOn(UserController.class).deleteUser(user.getId()))),
                        linkTo(methodOn(UserController.class).findAll()).withRel("users")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    ResponseEntity<CollectionModel<EntityModel<User>>> findAll() {
        List<EntityModel<User>> users = (List<EntityModel<User>>) StreamSupport.stream(userService.findAll().spliterator(), false)
                .map(employee -> new EntityModel<>(employee,
                        linkTo(methodOn(UserController.class).findOne(employee.getId())).withSelfRel()
                                .andAffordance(afford(methodOn(UserController.class).updateUser(null, employee.getId())))
                                .andAffordance(afford(methodOn(UserController.class).deleteUser(employee.getId()))),
                        linkTo(methodOn(UserController.class).findAll()).withRel("users")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CollectionModel<>(
                users,
                linkTo(methodOn(UserController.class).findAll()).withSelfRel()
                        .andAffordance(afford(methodOn(UserController.class).createUser(null)))));
    }

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody User user) {
        User createdUser = userService.create(user);

        Link findLink = linkTo(methodOn(UserController.class).findOne(createdUser.getId())).withSelfRel()
                .andAffordance(afford(methodOn(UserController.class).updateUser(null, createdUser.getId())))
                .andAffordance(afford(methodOn(UserController.class).deleteUser(createdUser.getId())));

        Link findAllLink = linkTo(methodOn(UserController.class).findAll()).withRel("users");

        return new EntityModel<>(createdUser, findLink, findAllLink)
                .getLink(IanaLinkRelations.SELF)
                .map(Link::getHref)
                .map(href -> {
                    try {
                        return new URI(href);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(uri -> ResponseEntity.ok().location(uri).build())
                .orElse(ResponseEntity.badRequest().body("Unable to create " + user));
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) {
        User updatedUser = userService.update(id, user);

        Link findLink = linkTo(methodOn(UserController.class).findOne(updatedUser.getId())).withSelfRel()
                .andAffordance(afford(methodOn(UserController.class).updateUser(null, updatedUser.getId())))
                .andAffordance(afford(methodOn(UserController.class).deleteUser(updatedUser.getId())));

        Link findAllLink = linkTo(methodOn(UserController.class).findAll()).withRel("users");

        return new EntityModel<>(updatedUser, findLink, findAllLink)
                .getLink(IanaLinkRelations.SELF)
                .map(Link::getHref)
                .map(href -> {
                    try {
                        return new URI(href);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(uri -> ResponseEntity.ok().location(uri).build())
                .orElse(ResponseEntity.badRequest().body("Unable to create " + user));
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
