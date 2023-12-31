package com.example.controller;

import com.example.model.domain.Issue;
import com.example.model.dto.CreateIssueRequestDto;
import com.example.model.dto.IssueDto;
import com.example.model.dto.SearchIssueDto;
import com.example.model.dto.UpdateIssueRequestDto;
import com.example.service.FilterSpecification;
import com.example.service.IssueService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class IssueController implements IssueApi {

    private final IssueService issueService;

    private FilterSpecification filterSpecification;

    @Override
    public Page<IssueDto> getAllIssues(Integer page, Integer size) {
        return issueService.getAllIssues(page, size);
    }

    @Override
    public IssueDto getOne(Long id) {
        return issueService.getOne(id);
    }

    @Override
    public IssueDto createIssue(@RequestBody @Valid CreateIssueRequestDto issue) {
        return issueService.createIssue(issue);
    }

    @Override
    public IssueDto updateIssue(@RequestBody @Valid UpdateIssueRequestDto issue) {
        return issueService.updateIssue(issue);
    }

    @Override
    public Page<IssueDto> filteredListWithCriteria(Integer page, Integer size, List<SearchIssueDto> searchIssueDtos) {
        Specification<Issue> searchSpecification = filterSpecification.getSearchSpecification(searchIssueDtos);
        return issueService.getAllIssues( searchSpecification, page, size);
    }
}
