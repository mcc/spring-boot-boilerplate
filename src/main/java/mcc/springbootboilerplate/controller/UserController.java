package mcc.springbootboilerplate.controller;

import mcc.springbootboilerplate.entity.MyUser;
import mcc.springbootboilerplate.exception.UserAlreadyExistException;
import mcc.springbootboilerplate.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    MyUserService myUserService;

    @GetMapping("/register")
    @ResponseBody
    public ResponseEntity register(@RequestParam("username") String username) throws UserAlreadyExistException {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.register(username, "asd"));
    }


    @GetMapping("/{id}/unlock")
    public ResponseEntity unlock(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.unlock(id));

    }
    @GetMapping("/{id}")
    public EntityModel<MyUser> findOne(@PathVariable Long id) {
        Class<UserController> controllerClass = UserController.class;

        // Start the affordance with the "self" link, i.e. this method.
        Link findOneLink = linkTo(methodOn(controllerClass).findOne(id)).withSelfRel();
        Link unlocakLink = linkTo(methodOn(controllerClass).unlock(id)).withRel("unlock");

        // Return the affordance + a link back to the entire collection resource.
        return EntityModel.of( myUserService.getById(id), //
                findOneLink, unlocakLink);
    }
}
