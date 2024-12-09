package com.example.springTrain.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.UsersRepository;

/***
 * UserDetailsService interface only has only method which
 * Locates the user based on the username.
 *  we will override with our own
 *  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException; 
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
    	Users user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

     // Convert the User entity to a Spring Security UserDetails object
        return User.withUsername(user.getEmail())
                   .password(user.getPassword())
                    .roles(user.getUsertype().name()) //.name() converts the enum value (like ADMIN, USER, etc.) to a String role name
                   .build(); // Finalizes and creates the UserDetails object with the username, password, and role
    }  
}