package com.example.springTrain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springTrain.repository.UsersRepository;
import com.example.springTrain.table.Users;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
     // Convert the User entity to a Spring Security UserDetails object
        return User.withUsername(user.getUsername())
                   .password(user.getPassword())
                   .roles(user.getUsertype().name())  // Assign roles from the database
                   .build();
    }
    
}