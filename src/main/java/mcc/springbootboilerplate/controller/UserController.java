package mcc.springbootboilerplate.controller;

import mcc.springbootboilerplate.exception.UserAlreadyExistException;
import mcc.springbootboilerplate.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class UserController {

    @Autowired
    MyUserService myUserService;

    @GetMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/register")
    @ResponseBody
    public String register(@RequestParam("username") String username) throws UserAlreadyExistException {
        return myUserService.register(username, "asd");
    }
}
