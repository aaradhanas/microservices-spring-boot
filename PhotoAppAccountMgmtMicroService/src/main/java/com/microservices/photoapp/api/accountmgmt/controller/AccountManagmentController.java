package com.microservices.photoapp.api.accountmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountManagmentController {

    // To get access to environment variables
    @Autowired
    private Environment environment;

    @GetMapping("/status")
    public String getStatus() {
        return "Account Management service works on port " + environment.getProperty("local.server.port");
    }
}
