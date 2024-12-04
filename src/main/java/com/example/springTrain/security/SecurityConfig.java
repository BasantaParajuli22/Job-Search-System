package com.example.springTrain.security;

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
            	.requestMatchers("/", "/login", "/employer/register", "/jobseeker/register",
            			"/css/**", "/js/**","/photo/**",
            			"/view/**","/search/**").permitAll() // Allow access to these pages
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/jobseekers/**").hasRole("JOBSEEKER")
                .requestMatchers("/employers/**").hasRole("EMPLOYER")
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
                 .deleteCookies("JSESSIONID","remember-me") // Delete the session cookie
                .permitAll()
            )
            //when clicked remember me in login form temporarily storing users username and password
            .rememberMe(rememberMe ->rememberMe
            		.key("secretKey")
            		.tokenValiditySeconds(10 * 24 * 60 * 60)//for 10 days
            		.rememberMeParameter("rememberMe")
            		.useSecureCookie(false)//for transfering cookie over https only
            		
            )
            .sessionManagement(session ->session
            		.invalidSessionUrl("/logout")
            		.maximumSessions(1)
            		.expiredUrl("/logout")
            		)
            ;
        
         // Using custom AccessDeniedHandler
//            to redirect user whose role doesnot match the content based on role
//            or requires different role
//            .exceptionHandling(exception -> exception
//            		.accessDeniedHandler(new CustomAccessDeniedHandler()))
//        ;
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
}