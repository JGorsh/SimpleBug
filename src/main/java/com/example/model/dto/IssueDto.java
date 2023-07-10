package com.example.model.dto;

import com.example.model.domain.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class IssueDto {

    private Long id;
    private String subject;
    private String description;
    private PersonDto person;
    private ProjectDto project;
    private List<Comment> comments;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Instant created;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Instant resolved;

}
