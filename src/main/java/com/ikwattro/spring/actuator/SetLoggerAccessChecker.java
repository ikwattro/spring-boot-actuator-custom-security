package com.ikwattro.spring.actuator;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SetLoggerAccessChecker {

    public boolean isAuthorizedToChangeLogs(Authentication authentication, HttpServletRequest request) {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            return ((User) authentication.getPrincipal()).getUsername().equals("admin");
        }

        return true;
    }
}
