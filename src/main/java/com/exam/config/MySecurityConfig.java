package com.exam.config;

import com.exam.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration class responsible for configuring authentication, authorization,
 * and JWT-based authentication filters for the application.
 *
 * @author AYUSH
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig {

    // Injecting required services and components

    /**
     * Service for loading user-specific data during authentication.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Customized authentication entry point for handling unauthorized access.
     */
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    /**
     * Custom JWT authentication filter for token-based authentication.
     */
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // ... Other methods and configurations ...

    /**
     * Configures the security filter chain to define access rules and authentication mechanisms.
     *
     * @param http The HttpSecurity instance to be configured.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeRequests(auth -> auth

                        .requestMatchers("/v1/generate_token","/v1/user/forgot_password",
                                "/v1/user/",
                                "/v1/user/confirm",
                                "/v1/user/forgot_user_name",
                                "/swagger-ui/*",
                                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(auth->auth
                        .authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement( session ->session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS));
        // Add the JWT authentication filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Defines the password encoder to be used for encoding and verifying passwords.
     *
     * @return An instance of BCryptPasswordEncoder for password hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder() ;
    }

    /**
     * Creates and configures the AuthenticationManager used for user authentication.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration instance.
     * @return An instance of AuthenticationManager for handling authentication.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
