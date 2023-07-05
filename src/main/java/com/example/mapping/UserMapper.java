package com.example.mapping;

import com.example.model.domain.User;
import com.example.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toEntity(UserDto userDto);

    UserDto fromEntity(User user);

}
