package com.example.ravi.androidclient;

public class User {
    String firstName;
    String lastName;
    String email;
    String identity;

    public User () {
        firstName = "hi";
        lastName = "hi";
        email = "hi";
        identity = "hi";
    }

    public User (String firstName, String lastName, String email, String identity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.identity = identity;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentity() {
        return identity;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
