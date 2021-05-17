package com.example.mymovies;

public class User {
    private String firstName;
    private String lastName;
    private String authorization;
    private Credentials credentials;

    //constructor for user
    public User (String username, String password, String firstName, String lastName, String authorization) {
        this.credentials = new Credentials(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorization = authorization;
    }

    //overload the constructor so that
    public User (Credentials credentials, String firstName, String lastName, String authorization){
        this.credentials = credentials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorization = authorization;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}

