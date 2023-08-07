package com.exam.config;

import com.exam.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.authorizeHttpRequests(
//                auth -> {
//                    auth
//                            .anyRequest().authenticated();
//                });
//
//        http.sessionManagement(
//                session ->
//                        session.sessionCreationPolicy(
//                                SessionCreationPolicy.STATELESS)
//        );
//
//        //http.formLogin();
//        http.httpBasic(withDefaults());
//
//        http.csrf(csrf -> csrf.disable());
//
//        //http.csrf(AbstractHttpConfigurer::disable);
//
//        http.headers(headers -> headers.frameOptions(frameOptionsConfig-> frameOptionsConfig.disable()));
//
//        // http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
//
//        return http.build();
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/generate_token","/user/").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(auth->auth
                        .authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement( session ->session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
