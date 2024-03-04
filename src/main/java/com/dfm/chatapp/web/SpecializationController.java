package com.dfm.chatapp.web;

import com.dfm.chatapp.dto.SpecializationDTO;
import com.dfm.chatapp.exception.BadRequestException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import com.dfm.chatapp.service.SpecializationService;

@RestController
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @PostMapping("/specialization")
    public ResponseEntity<SpecializationDTO> addSpecialization(@RequestBody SpecializationDTO specializationDTO) {
        SpecializationDTO createdSpecialization = specializationService.createSpecialization(specializationDTO);
        return new ResponseEntity<>(createdSpecialization, HttpStatus.CREATED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/specialization/{id}/{field}")
    public boolean updateSpecialization(@PathVariable Long id, @PathVariable String field,
            @RequestBody boolean value) {
        return specializationService.setDirtyFields(id, field, value);
    }

    @DeleteMapping("/specialization/{id}")
    public ResponseEntity<SpecializationDTO> deleteSpecialization(@PathVariable Long id) {
        specializationService.deleteSpecialization(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/specializations")
    public Iterable<SpecializationDTO> getAllSpecializations() {
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/specializations/true")
    public Iterable<Map<String, Object>> getAllTrueSpecializations() {
        return specializationService.getAllTrueSpecializations();
    }

    @GetMapping("/specialization/{id}")
    public Map<String, Object> getSpecialization(@PathVariable Long id) {
        return specializationService.getSpecializationById(id);
    }
}