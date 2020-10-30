package com.microservices.app.service;

import com.microservices.app.model.request.CreateUserDetailsRequest;
import com.microservices.app.model.response.UserDetails;

public interface IUserService {
    UserDetails createUser(CreateUserDetailsRequest userDetails);
}
