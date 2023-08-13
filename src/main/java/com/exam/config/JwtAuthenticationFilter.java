package com.exam.config;

import com.exam.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Custom JWT-based authentication filter that intercepts incoming HTTP requests,
 * extracts JWT tokens from the Authorization header, validates them, and sets the
 * authenticated user in the SecurityContextHolder.
 *
 * @author AYUSH
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Injecting required services and components

    /**
     * Service for loading user-specific data during authentication.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Utility class for working with JWT tokens.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Intercepts and processes incoming HTTP requests to perform JWT-based authentication.
     *
     * @param request The incoming HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param filterChain The filter chain for further request processing.
     * @throws ServletException If a servlet-related exception occurs.
     * @throws IOException If an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract the JWT token from the Authorization header
       final String requestTokenHeader =  request.getHeader("Authorization");
        System.out.println(requestTokenHeader);
        String username = null;
        String jwtToken =null;

        // Check if the token is present and properly formatted
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){

            try{
                // Extract the JWT token and username from the token
                jwtToken = requestTokenHeader.substring(7);
                username = this.jwtUtil.extractUsername(jwtToken);
            }catch(ExpiredJwtException e){
                e.printStackTrace();
                System.out.println("jwt token had expired");
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("erorr occured");
            }

            // If username is present and user is not already authenticated
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if(this.jwtUtil.validateToken(jwtToken,userDetails)){
                    //token is valid

                    // Token is valid, set the user in the SecurityContextHolder
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }


        }else{
            // Invalid token format
            System.out.println("Invalid Token ");
           throw  new IllegalStateException("Invalid Token");

        }
        // Invalid token format
        filterChain.doFilter(request,response);
    }
}
