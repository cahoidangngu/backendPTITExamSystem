package com.quanchun.backendexamsystem.mappers;

import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.models.UserDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    @Named("toResponse")
    UserDTO user2UserDTO(User user);

    @IterableMapping(qualifiedByName = "toResponse")
    List<UserDTO> toResponses(List<User> users);

    @Named("toPOJO")
    User userDTO2User(UserDTO userDTO);

    @IterableMapping(qualifiedByName = "toPOJO")
    List<User> toPOJOs(List<UserDTO> userDTOS);


}
