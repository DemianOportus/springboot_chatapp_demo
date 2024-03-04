package com.dfm.chatapp.service;

import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.exception.NotFoundException;
import com.dfm.chatapp.exception.ServiceException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

// UserService.java
public interface UserService {

    // Create a new user
    UserDTO createUser(UserDTO user) throws MessagingException;

    boolean emailInDatabase(String email);

    // Update an existing user
    UserDTO updateUser(UserDTO user) throws NotFoundException;

    // Delete an existing user by id
    void deleteUser(Long id);

    // Get user by id
    UserDTO getUserById(Long id);

    // Get user by username
    UserDTO getUser(String firstName, String lastName);

    UserDTO getUser(String lastName) throws ServiceException;

    // Get all users
    Iterable<UserDTO> getAllUsers();

    Iterable<UserDTO> getDoctors();

    Iterable<UserDTO> getPatients();

    ResponseEntity<String> handleConfirmationRequest(String confirmationToken) throws MessagingException;

    ResponseEntity<String> registerUser(UserDTO userDto) throws MessagingException;

    boolean unsubscribeUser(String email);
}