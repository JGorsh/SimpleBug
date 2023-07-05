package com.example.mapping;

import com.example.model.domain.Issue;
import com.example.model.dto.CreateIssueRequestDto;
import com.example.model.dto.IssueDto;
import com.example.model.dto.UpdateIssueRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {ProjectMapper.class, UserMapper.class, CommentMapper.class})
public interface IssueMapper {

    IssueDto fromEntity(Issue issue);

    Issue toEntity(IssueDto issueDto);

    Issue toEntity(CreateIssueRequestDto issueDto);

    Issue updateEntityFromDto(UpdateIssueRequestDto issueDto, @MappingTarget Issue issue);

}
