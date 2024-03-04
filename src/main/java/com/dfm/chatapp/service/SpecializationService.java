package com.dfm.chatapp.service;

import java.util.List;
import com.dfm.chatapp.entity.Specialization;
import com.dfm.chatapp.exception.NotFoundException;
import com.dfm.chatapp.dto.SpecializationDTO;
import java.util.HashMap;
import java.util.Map;

public interface SpecializationService {

    // Create a new specialization
    SpecializationDTO createSpecialization(SpecializationDTO specializationDTO);

    // Update an existing specialization
    // SpecializationDTO update(SpecializationDTO specializationDTO)

    // Delete a specialization by id
    void deleteSpecialization(Long id);

    // Get Specialization by id
    Map<String, Object> getSpecializationById(Long id);

    Specialization getSpecializationById2(Long id);

    // Get all specializations
    Iterable<SpecializationDTO> getAllSpecializations();

    List<Map<String, Object>> getAllTrueSpecializations();

    boolean setDirtyFields(Long id, String field, boolean value);

}
