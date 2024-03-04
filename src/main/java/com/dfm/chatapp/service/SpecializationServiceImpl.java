package com.dfm.chatapp.service;

import com.dfm.chatapp.dto.SpecializationDTO;
import com.dfm.chatapp.entity.Specialization;
import com.dfm.chatapp.exception.BadRequestException;
import com.dfm.chatapp.exception.NotFoundException;
import com.dfm.chatapp.exception.ServiceException;
import com.dfm.chatapp.mapper.ISpecializationMapper;
import com.dfm.chatapp.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private ISpecializationMapper specializationMapper;

    // Create a new specialization
    @Override
    public SpecializationDTO createSpecialization(SpecializationDTO specializationDTO) {
        boolean anySpecializationSet = false;
        for (Field field : SpecializationDTO.class.getDeclaredFields()) {
            if (field.getType() != boolean.class) {
                continue;
            }
            field.setAccessible(true);
            try {
                if (field.getBoolean(specializationDTO)) {
                    anySpecializationSet = true;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (!anySpecializationSet) {
            throw new BadRequestException("No specialization selected");
        }

        return specializationMapper.entityToDto(
                specializationRepository.saveAndFlush(specializationMapper.dtoToEntity(specializationDTO)));

    }

    // Update an existing specialization

    // Delete a specialization by id
    @Override
    public void deleteSpecialization(Long id) throws ServiceException {
        if (specializationRepository.existsById(id)) {
            specializationRepository.deleteById(id);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    // Get Specialization by id
    @Override
    public Specialization getSpecializationById2(Long id) {
        return specializationRepository.findById(id).orElse(null);
    }

    public Map<String, Object> getSpecializationById(Long id) {
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Specialization not found"));
        return specialization.getTrueFields();
    }

    // Get all specializations
    @Override
    public Iterable<SpecializationDTO> getAllSpecializations() {
        return specializationMapper.entityToDtos(specializationRepository.findAll());
    }

    @Override
    public List<Map<String, Object>> getAllTrueSpecializations() {
        Iterable<Specialization> specializations = specializationRepository.findAll();
        return StreamSupport.stream(specializations.spliterator(), false)
                .map(specialization -> {
                    Map<String, Object> trueFields = specialization.getTrueFields();
                    trueFields.put("id", specialization.getId());
                    return trueFields;
                })
                .collect(Collectors.toList());
    }

    // PATCH instead of PUT
    @Override
    public boolean setDirtyFields(Long id, String fieldName, boolean value) {
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Specialization not found"));
        try {
            Field field = Specialization.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(specialization, value);
            specializationRepository.save(specialization);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }

}
