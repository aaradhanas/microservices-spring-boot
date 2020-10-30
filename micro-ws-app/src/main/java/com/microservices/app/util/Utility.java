package com.microservices.app.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utility {

    public String generateUserId() {
        return UUID.randomUUID().toString();
    }
}
