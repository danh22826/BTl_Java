package com.example.demo.dto.request;

public class LoginRequest {
    private String username;
    private String password;

    // Getters và Setters bắt buộc phải có
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}