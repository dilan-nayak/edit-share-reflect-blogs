package com.example.blogbackend.config;

import com.example.blogbackend.security.AuthEntryPointJwt;
import com.example.blogbackend.security.AuthTokenFilter;
// UserDetailsServiceImpl is not directly autowired here anymore, but used by AuthenticationManagerBuilder implicitly
// import com.example.blogbackend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize, @PostAuthorize, etc. for service-layer security
public class SecurityConfig {

    // UserDetailsServiceImpl is used by the AuthenticationManager,
    // which is configured via AuthenticationConfiguration.
    // No need to Autowire UserDetailsServiceImpl directly here if not using it explicitly
    // in this class for configuring AuthenticationManagerBuilder (which is the older way).
    // @Autowired
    // private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless REST API
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Set custom entry point
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Allow auth endpoints (login/register)
                .requestMatchers("/api/posts/**").permitAll() // Allow public post viewing (adjust as needed for specific GETs)
                // .requestMatchers("/api/test/**").permitAll() // Example for public test endpoints
                .anyRequest().authenticated() // Require authentication for all other /api/** routes
            );

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
