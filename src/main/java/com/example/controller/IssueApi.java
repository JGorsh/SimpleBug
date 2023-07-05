package com.example.controller;

import com.example.model.domain.Issue;
import com.example.model.dto.CreateIssueRequestDto;
import com.example.model.dto.IssueDto;
import com.example.model.dto.UpdateIssueRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


}
