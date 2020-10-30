package com.microservices.photoapp.api.users.security;

import com.microservices.photoapp.api.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${gateway.ip}")
    private String gatewayIpAddress;

    private final UserService userService;

    private final Environment environment;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService, Environment  environment, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.environment = environment;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Since we will use JWT for authentication/authorization
        http.csrf().disable();
        // Allow requests only from API Gateway
        // http.authorizeRequests().antMatchers("/**").hasIpAddress(gatewayIpAddress)
        http.authorizeRequests().antMatchers("/**").permitAll()
            .and()
            .addFilter(getAuthenticationFilter());
        // http.authorizeRequests().antMatchers("/users/**").permitAll();

        // This needs to be done to have the H2 console displayed in webpage. H2 console runs inside a frame.
        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =  new AuthenticationFilter(userService, environment, authenticationManager());
        
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
