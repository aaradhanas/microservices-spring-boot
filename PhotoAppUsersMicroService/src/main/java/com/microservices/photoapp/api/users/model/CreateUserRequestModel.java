package com.microservices.photoapp.api.users.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * This model class is used between client & Presentation Layer (Controller)
 */
public class CreateUserRequestModel {

    @NotEmpty(message = "First name cannot be empty")
    @Size(min=2, message = "First name must not be less than 2 characters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min=2, message = "Last name must not be less than 2 characters")
    private String lastName;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min=8, max=16, message = "Password must atleast be 8 characters and not more than 16 characters")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not of correct format")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
