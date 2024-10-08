package com.example.springTrain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/jobseeker/**").hasRole("JOBSEEKER")
                .requestMatchers("/employer/**").hasRole("EMPLOYER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // Redirect to /home after successful login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
    
//   @Bean
//    public UserDetailsService userDetailsService() {
//        // Define users for testing. In a real-world application, you would fetch these from a database.
//        UserDetails jobSeeker = User.withDefaultPasswordEncoder()
//            .username("jobseeker")
//            .password("password")
//            .roles("JOBSEEKER")
//            .build();
//
//        UserDetails employer = User.withDefaultPasswordEncoder()
//            .username("employer")
//            .password("password")
//            .roles("EMPLOYER")
//            .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//            .username("admin")
//            .password("adminpassword")
//            .roles("ADMIN")
//            .build();
//
//        return new InMemoryUserDetailsManager(jobSeeker, employer, admin);  // Add all users to in-memory storage
//    }
}