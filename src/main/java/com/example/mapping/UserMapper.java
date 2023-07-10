package com.example.mapping;

import com.example.model.domain.Person;
import com.example.model.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    Person toEntity(PersonDto personDto);

    PersonDto fromEntity(Person person);

}
