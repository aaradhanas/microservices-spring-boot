package com.microservices.photoapp.api.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.photoapp.api.users.model.LoginRequestModel;
import com.microservices.photoapp.api.users.service.UserService;
import com.microservices.photoapp.api.users.shared.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;

    private final Environment environment;

    public AuthenticationFilter(UserService userService, Environment environment, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);

    }
    /*
        This method will be called by Spring framework
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestModel loginCredentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginCredentials.getEmail(),
                            loginCredentials.getPassword(),
                            new ArrayList<>())
                    );
        } catch (IOException e) {
          throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserDTO userDetails = userService.getUserDetailsByEmail(userName);

        String expirationTimeStr = environment.getProperty("jwt.expiration_time");

        String jwt = Jwts.builder()
                    .setSubject(userDetails.getUserId())
                    .setExpiration(new Date( System.currentTimeMillis() + (expirationTimeStr != null ? Long.parseLong(expirationTimeStr) : 0) ))
                    .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.secret"))
                    .compact();

        response.addHeader("jwt", jwt);
        response.addHeader("userId", userDetails.getUserId());
    }
}
