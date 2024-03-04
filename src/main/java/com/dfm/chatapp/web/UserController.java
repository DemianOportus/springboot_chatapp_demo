package com.dfm.chatapp.web;

import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.exception.NotFoundException;
import com.dfm.chatapp.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDto) throws MessagingException {
        return userService.registerUser(userDto);
    }


    @RequestMapping(value = "/account/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) throws MessagingException {
        return userService.handleConfirmationRequest(confirmationToken);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO.getLastName(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<UserDTO>> getUsers() {
        Iterable<UserDTO> usersDTO = userService.getAllUsers();
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping("/doctors")
    public ResponseEntity<Iterable<UserDTO>> getDoctors() {
        Iterable<UserDTO> doctorsDTO = userService.getDoctors();
        return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
    }

    @GetMapping("/patients")
    public ResponseEntity<Iterable<UserDTO>> getPatients() {
        Iterable<UserDTO> patientsDTO = userService.getPatients();
        return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
    }


    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) throws NotFoundException {
        return userService.updateUser(userDTO);
    }

    @PutMapping("/users/unsubscribe")
    public ResponseEntity<String> unsubscribeUser(@RequestParam String email) {
        boolean success = userService.unsubscribeUser(email);
        if (success) {
            return new ResponseEntity<>("User has been unsubscribed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User could not be unsubscribed", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
