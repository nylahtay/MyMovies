package com.example.mymovies;

public class Credentials {
    private String Username;
    private String Password;

    Credentials(String username, String password)
    {
        this.Username = username;
        this.Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

}
