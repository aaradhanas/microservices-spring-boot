package com.microservices.photoapp.api.users.service;

import com.microservices.photoapp.api.users.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserDetailsByEmail(String email);
}
