package com.exam.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Custom implementation of AuthenticationEntryPoint used to handle unauthorized access attempts.
 * This component is invoked by Spring Security when an authentication error occurs,
 * and it sends an HTTP response with an appropriate error code and message.
 *
 * @author  AYUSH
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commences an authentication scheme.
     * <p>
     * This method is invoked by Spring Security when an authentication exception occurs.
     * It sends an HTTP response with an unauthorized error code and message.
     *
     * @param request       The HttpServletRequest that resulted in an AuthenticationException.
     * @param response      The HttpServletResponse so that the user agent can begin authentication.
     * @param authException The AuthenticationException that caused the invocation.
     * @throws IOException If an I/O exception occurs while sending the response.
     * @throws ServletException If a servlet-related exception occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Send an HTTP response with an unauthorized error code and message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized : Server");

    }
}
