package com.example.mapping;

import com.example.model.domain.Person;
import com.example.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    Person toEntity(UserDto userDto);

    UserDto fromEntity(Person person);

}
