package com.dfm.chatapp.mapper;

import com.dfm.chatapp.dto.SpecializationDTO;
import com.dfm.chatapp.entity.Specialization;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ISpecializationMapper {

    SpecializationDTO entityToDto(Specialization specialization);

    Specialization dtoToEntity(SpecializationDTO specializationDTO);

    List<SpecializationDTO> entityToDtos(Iterable<Specialization> specializations);

    List<Specialization> dtoToEntity(Iterable<SpecializationDTO> specializationDTOs);

}
