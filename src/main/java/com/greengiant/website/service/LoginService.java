package com.greengiant.website.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    void login(String username, String password);

    String getRole(String username);
}
