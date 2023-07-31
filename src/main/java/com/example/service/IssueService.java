package com.example.service;

import com.example.model.domain.Issue;
import com.example.model.dto.CreateIssueRequestDto;
import com.example.model.dto.IssueDto;
import com.example.model.dto.UpdateIssueRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;

public interface IssueService {
    Page<IssueDto> getAllIssues(Integer page, Integer size);

    Page<IssueDto> getAllIssues(Specification<Issue> specification, Integer page, Integer size);

    IssueDto getOne(@PathVariable Long id);

    IssueDto createIssue(CreateIssueRequestDto issue);

    IssueDto updateIssue(UpdateIssueRequestDto issue);

}
