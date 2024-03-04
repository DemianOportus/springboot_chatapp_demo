package com.dfm.chatapp.security.manager;

import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private UserService userServiceImpl;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Get the user with the given username frocm the UserService
        UserDTO user = userServiceImpl.getUser(authentication.getName());
        // Check if the given password matches the user's password
        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            // If the passwords don't match, throw a BadCredentialsException
            throw new BadCredentialsException("You provided an incorrect password.");
        }
        // If the passwords match, create and return a new
        // UsernamePasswordAuthenticationToken
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
    }
}
