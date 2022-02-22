package com.app.dportshipper.model.response;

import java.util.List;

public class ResLoginShipper {

    private int status;
    private String username;
    private String email;
    private String role;
    private String token;
    private List<ResModule> module;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ResModule> getModule() {
        return module;
    }

    public void setModule(List<ResModule> module) {
        this.module = module;
    }
}
