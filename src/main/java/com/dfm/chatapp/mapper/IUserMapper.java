package com.dfm.chatapp.mapper;

import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

// https://www.baeldung.com/mapstruct

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IUserMapper {

    UserDTO entityToDto(User user);

    User dtoToEntity(UserDTO userDto);

    List<User> dtoToEntity(Iterable<UserDTO> userDtos);

    List<UserDTO> entityToDto(Iterable<User> users);

}
