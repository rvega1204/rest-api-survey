/**
 * Configuration class for Spring Security.
 * 
 * This class sets up the security configuration for the application, including:
 * - Creating an in-memory user details manager with predefined users.
 * - Providing a password encoder bean.
 * - Configuring the security filter chain to require authentication for all requests,
 *   use basic authentication, disable CSRF for POST or PUT requests, and disable frame
 *   options to allow access to the H2 console.
 * 
 * Annotations:
 * - @Configuration: Indicates that this class declares one or more @Bean methods and
 *   may be processed by the Spring container to generate bean definitions and service
 *   requests for those beans at runtime.
 */
package com.rvg.springboot.restapi.security;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

    // Bean to create an in-memory user details manager with two users
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {

        UserDetails userDetails1 = createNewUser("admin", "admin");
        UserDetails userDetails2 = createNewUser("rvg", "asdf");

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    // Helper method to create a new user with encoded password
    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);

        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
        return userDetails;
    }

    // Bean to provide a password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean to configure the security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Require authentication for any request
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated());

        // Use basic authentication
        http.httpBasic(withDefaults());

        // Disable CSRF for POST or PUT requests
        http.csrf(csrf -> csrf.disable());

        // Disable frame options to allow H2 console access
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }
}
