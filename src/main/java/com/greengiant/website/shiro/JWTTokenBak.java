package com.greengiant.website.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用于JWT的token
 */
public class JWTTokenBak implements AuthenticationToken {
    private String token;

    public JWTTokenBak(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
