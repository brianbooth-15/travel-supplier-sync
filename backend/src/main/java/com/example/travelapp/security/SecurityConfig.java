package com.example.travelapp.security;

import com.example.travelapp.service.CustomUserDetailsService; // Custom user details service
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; enable in production with proper setup
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register").permitAll() // Allow open access to login and register
                .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict access to admin endpoints
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Allow USER and ADMIN to access /user
                .anyRequest().authenticated() // Any other request requires authentication
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page URL
                .permitAll() // Allow everyone to access the login page
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Custom logout URL
                .permitAll() // Allow everyone to access the logout endpoint
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Using BCrypt for password encoding
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Use AuthenticationConfiguration to expose AuthenticationManager as a bean
        return authenticationConfiguration.getAuthenticationManager();
    }
}
