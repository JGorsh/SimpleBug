package com.example.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
}
