package com.microservices.photoapp.api.gateway.security;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(environment.getProperty("token.header.name"));

        if(authorizationHeader == null || ! authorizationHeader.startsWith(environment.getProperty("token.header.prefix"))) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(environment.getProperty("token.header.name"));
        if(authorizationHeader == null) {
            return null;
        }

        String jwt = authorizationHeader.replace(environment.getProperty("token.header.prefix"), "");

        String userId = Jwts.parser()
                        .setSigningKey(environment.getProperty("jwt.secret"))
                        .parseClaimsJws(jwt)
                        .getBody()
                        .getSubject();
        if(userId == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }
}
