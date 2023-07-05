package com.example.mapping;

import com.example.model.domain.Project;
import com.example.model.dto.ProjectDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {

    Project toEntity(ProjectDto projectDto);

    ProjectDto fromEntity(Project project);

}
