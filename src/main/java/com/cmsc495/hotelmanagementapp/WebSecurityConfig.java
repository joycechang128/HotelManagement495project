package com.cmsc495.hotelmanagementapp;
/*
 * File: WebSecurityConfig.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/WebSecurityConfig.java
 * Package: com.cmsc495.hotelmanagementapp
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-03-30
 * Last Modified: 2024-03-30
 * Description: This file contains the configuration class for web security settings,
 * 				preventing the unauthorized access to main and management pages.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()								// require authentication to access these pages
                .requestMatchers("/main", "/room", "/customer", "/reservation",
                		"/billing", "/housekeeping", "/visualreport").authenticated()
                .anyRequest().permitAll()						// allow access to all other pages
                .and()
            .formLogin()
                .loginPage("/login")							// specify a custom login page
                .defaultSuccessUrl("/main")						// redirect to the main page after successful login
                // redirect the user back to the loginPage with an error message
                .failureHandler(authenticationFailureHandler())	// handle authentication failure
                .permitAll();									// allow access to the login page
// The code below is equivalent to the showLogoutPage() method in UserController.java, either one works
//                .and()
//            .logout()
//                .logoutSuccessUrl("/login")					// redirect to the login page after logout
//                .permitAll();									// allow access to the logout functionality
        return http.build();
    }
    
    // the method is used to configure password encoding in Spring Security
    @Bean
    public PasswordEncoder passwordEncoder() {
    	// use NoOpPasswordEncoder to disable password encoding
        return NoOpPasswordEncoder.getInstance();
    }
    
    // the method for handling authentication failure
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }
}