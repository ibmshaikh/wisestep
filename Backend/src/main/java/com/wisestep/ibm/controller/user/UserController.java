package com.wisestep.ibm.controller.user;

import com.wisestep.ibm.model.user.User;
import com.wisestep.ibm.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<Object> createUser(@Validated @RequestBody User user) {
        return userService.createUser(user);
    }


    @PostMapping("/login")
    private ResponseEntity<Object> login(@RequestBody User userLogin) {
        return userService.login(userLogin);
    }

    @GetMapping("/session/{sessionID}")
    private ResponseEntity<Object> getUser(@PathVariable String sessionID) {
        return userService.getUser(sessionID);

    }

    @GetMapping("/session/invalidate/{email}")
    private ResponseEntity<Object> invalidateSession(@PathVariable String email) {
        return userService.invalidateSession(email);
    }

    @GetMapping("/logout/{email}")
    private ResponseEntity<Object> loggedOut(@PathVariable String email) {
        return userService.loggedOut(email);
    }
}
