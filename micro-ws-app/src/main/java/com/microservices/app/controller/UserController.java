package com.microservices.app.controller;

import com.microservices.app.exception.UserServiceException;
import com.microservices.app.model.request.CreateUserDetailsRequest;
import com.microservices.app.model.request.UpdateUserDetailsRequest;
import com.microservices.app.model.response.UserDetails;
import com.microservices.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {

    private final Map<String, UserDetails> users = new HashMap<>();

    // Need to specify qualifier if there are multiple implementations of the same interface
    @Autowired
    @Qualifier("userService")
    IUserService userService;

    @GetMapping
    public String getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value="limit", defaultValue = "50") int limit,
                           @RequestParam(value="sort", required = false) String sort) {
        return "Get users API was called for page = " + page + " and limit = " + limit + " and sort = " + sort;
    }

    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDetails> getUser(@PathVariable String userId) {
        if(users.containsKey(userId)) {
            return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity<UserDetails> createUser(@RequestBody @Valid CreateUserDetailsRequest userDetails) {
        UserDetails user = userService.createUser(userDetails);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}",
                consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDetails> updateUser(@PathVariable String userId, @RequestBody UpdateUserDetailsRequest userDetails) {
        if(users.containsKey(userId)) {
            UserDetails savedUser =  users.get(userId);
            if(savedUser != null) {
                savedUser.setFirstName(userDetails.getFirstName());
                savedUser.setLastName(userDetails.getLastName());
            }
            users.put(userId, savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if(users.containsKey(userId)) {
            users.remove(userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
