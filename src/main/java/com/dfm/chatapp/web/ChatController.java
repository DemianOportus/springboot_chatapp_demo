package com.dfm.chatapp.web;

import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

// NEVER RETURNS OR PARAMETER ENTITY. Only DTO's
public class ChatController {

    @Autowired
    private UserService userService;
    
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) throws MessagingException {
        return userService.createUser(userDTO);
    }
}
