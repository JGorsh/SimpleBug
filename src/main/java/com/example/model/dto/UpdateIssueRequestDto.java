package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class UpdateIssueRequestDto {

    @NotNull
    private Long id;
    @NotNull
    private String subject;
    private String description;
    @NotNull
    private UserDto user;
    @NotNull
    private ProjectDto project;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Instant created;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Instant resolved;
}
