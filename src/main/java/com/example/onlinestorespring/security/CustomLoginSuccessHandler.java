package com.example.onlinestorespring.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        boolean isAdmin = false;
        boolean isUser = false;

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
            }
        }

        if (isAdmin) {
            response.sendRedirect("/admin/home");
        } else if (isUser) {
            response.sendRedirect("/categories");
        } else {
            response.sendRedirect("/login?error");
        }
    }
}
