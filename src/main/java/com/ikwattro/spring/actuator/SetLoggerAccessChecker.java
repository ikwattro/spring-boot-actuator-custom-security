package com.ikwattro.spring.actuator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SetLoggerAccessChecker {

    public boolean isAuthorizedToChangeLogs(Authentication authentication, HttpServletRequest request) {
        return ((User) authentication.getPrincipal()).getUsername().equals("admin");
    }
}
