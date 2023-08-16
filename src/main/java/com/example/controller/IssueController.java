package com.example.controller;

import com.example.model.domain.Issue;
import com.example.model.dto.*;
import com.example.service.CommentService;
import com.example.service.FilterSpecification;
import com.example.service.IssueService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class IssueController implements IssueApi {

    private final IssueService issueService;

    private FilterSpecification filterSpecification;

    private final CommentService commentService;

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

    @Override
    public Page<CommentDto> getAllComments(Long issueId, Integer page, Integer size) {
        return commentService.getAllComment(issueId, page, size);
    }
}
