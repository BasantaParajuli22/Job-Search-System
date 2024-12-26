package com.example.springTrain.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springTrain.entity.Users;
import com.example.springTrain.enums.UserStatus;
import com.example.springTrain.service.UsersService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class BlockedUserFilter extends OncePerRequestFilter {

    @Autowired
    @Lazy
    private UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String email = UserAuthorization.getLoggedInUserEmail();
        if (email != null) {
            Users user = usersService.findByEmail(email);
            if (user != null && UserStatus.BLOCKED.equals(user.getUserStatus())) {
                // Avoid redirect loop by checking if the user is already on the access-denied page
                if (!request.getRequestURI().equals("/access-denied")) {
                    response.sendRedirect("/access-denied");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
