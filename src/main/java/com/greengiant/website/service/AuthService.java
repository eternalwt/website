package com.greengiant.website.service;

public interface AuthService {

    void login(String username, String password, boolean isRememberMe);

}
