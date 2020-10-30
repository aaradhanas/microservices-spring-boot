package com.microservices.app.service.impl;

import com.microservices.app.model.request.CreateUserDetailsRequest;
import com.microservices.app.model.response.UserDetails;
import com.microservices.app.service.IUserService;
import com.microservices.app.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// Need to give name if there are multiple implementations of the same interface
@Service("userService")
public class UserServiceImpl implements IUserService {

    private Map<String, UserDetails> users;

    //@Autowired
    private Utility utility;

    @Autowired
    public UserServiceImpl(Utility utility) {
        this.utility = utility;
    }

    @Override
    public UserDetails createUser(CreateUserDetailsRequest userDetails) {
        UserDetails userResponse = new UserDetails();
        userResponse.setFirstName(userDetails.getFirstName());
        userResponse.setLastName(userDetails.getLastName());
        userResponse.setEmail(userDetails.getEmail());

        String userId = utility.generateUserId();
        userResponse.setUserId(userId);

        if(users == null) {
            users = new HashMap<>();
        }
        users.put(userId, userResponse);
        return userResponse;
    }
}
