package com.example.travelapp.security;

import com.example.travelapp.service.CustomUserDetailsService; // Custom user details service
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set custom UserDetailsService and password encoder for authentication
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity (can be enabled for production)
            .authorizeRequests()
                .antMatchers("/login", "/register").permitAll() // Allow open access to login and register
                .antMatchers("/admin/**").hasRole("ADMIN") // Restrict access to admin endpoints
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Allow USER and ADMIN to access /user
                .anyRequest().authenticated() // Any other request needs authentication
            .and()
                .formLogin()
                    .loginPage("/login") // Custom login page URL
                    .permitAll()
            .and()
                .logout()
                    .permitAll(); // Allow all users to logout
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Using BCrypt for password encoding (consider using Argon2 or PBKDF2 in production)
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Expose the AuthenticationManager as a bean
        return super.authenticationManagerBean();
    }
}
