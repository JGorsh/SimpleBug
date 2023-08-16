package com.example.controller;

import com.example.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("issues")
public interface IssueApi {

    @GetMapping()
    Page<IssueDto> getAllIssues(@RequestParam(required = false, defaultValue = "0") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("{id}")
    IssueDto getOne(@PathVariable Long id);

    @PostMapping()
    IssueDto createIssue(CreateIssueRequestDto issue);

    @PutMapping
    IssueDto updateIssue(UpdateIssueRequestDto issue);

    @PostMapping("filters")
    Page<IssueDto> filteredListWithCriteria(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer size,
                                         @RequestBody(required = false) List<SearchIssueDto> searchIssueDtos);

    @GetMapping("comments")
    Page<CommentDto> getAllComments(@RequestParam(required = false) Long issueId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size);
}
