package com.example.springTrain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/", "/login", "/employer/register", "/jobseeker/register", "/css/**", "/js/**","/photo/**","/search","/view/jobposts").permitAll() // Allow access to these pages
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
            	.logoutUrl("/logout") // Custom logout URL
                 .logoutSuccessUrl("/login?logout") // Redirect after successful logout
                 .invalidateHttpSession(true) // Invalidate session
                 .deleteCookies("JSESSIONID") // Delete the session cookie
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
    
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        // Define users for testing. In a real-world application, you would fetch these from a database.
//        return new InMemoryUserDetailsManager(
//            User.withUsername("jobseeker")
//                .password(passwordEncoder().encode("password")) // Encode password
//                .roles("JOBSEEKER")
//                .build(),
//            User.withUsername("employer")
//                .password(passwordEncoder().encode("password")) // Encode password
//                .roles("EMPLOYER")
//                .build(),
//            User.withUsername("admin")
//                .password(passwordEncoder().encode("adminpassword")) // Encode password
//                .roles("ADMIN")
//                .build()
//        );  // Add all users to in-memory storage
//    }
}