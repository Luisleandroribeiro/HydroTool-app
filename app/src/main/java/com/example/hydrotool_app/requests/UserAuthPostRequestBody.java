package com.example.hydrotool_app.requests;

public class UserAuthPostRequestBody {
    private String username; // Necessário para registro
    private String email;
    private String password;

    public UserAuthPostRequestBody(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters e Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
