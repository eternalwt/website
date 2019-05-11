package com.greengiant.website.service;

import com.greengiant.website.model.Role;

public interface RoleService {
    String getRole(String username);

    void addRole(Role role);

}
