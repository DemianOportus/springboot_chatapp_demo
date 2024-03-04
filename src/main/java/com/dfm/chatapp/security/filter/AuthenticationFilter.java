package com.dfm.chatapp.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.security.SecurityConstants;
import com.dfm.chatapp.security.manager.CustomAuthenticationManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager customAuthenticationManager;

    /**
     * This method is called when an authentication request is made. It reads the
     * request body
     * and attempts to authenticate the user using the given credentials.
     *
     * @param request  The HTTP request.
     * @param response The HTTP response.
     * @return The result of the authentication attempt.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // Read the user credentials from the request body
            UserDTO userDto = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);

            // Create an authentication token using the user credentials
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getLastName(),
                    userDto.getPassword());

            // Attempt to authenticate the user using the given credentials
            return customAuthenticationManager.authenticate(authentication);

        } catch (IOException e) {
            // Log the error and throw a runtime exception
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * This method is called when the authentication attempt is successful.
     *
     * @param request    The HTTP request.
     * @param response   The HTTP response.
     * @param chain      The filter chain.
     * @param authResult The result of the authentication attempt.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // Log that the authentication attempt was successful
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));

        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);

    }

    /**
     * This method is called when the authentication attempt fails.
     *
     * @param request  The HTTP request.
     * @param response The HTTP response.
     * @param failed   The exception that caused the authentication attempt to fail.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

}
