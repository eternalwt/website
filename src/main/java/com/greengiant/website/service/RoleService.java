package com.greengiant.website.service;

import com.greengiant.website.pojo.model.Role;

public interface RoleService {
    String getRole(String username);

    void addRole(Role role);

}
