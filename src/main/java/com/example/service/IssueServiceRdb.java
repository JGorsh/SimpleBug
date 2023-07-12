package com.example.service;

import com.example.mapping.IssueMapper;
import com.example.model.dto.CreateIssueRequestDto;
import com.example.model.dto.IssueDto;
import com.example.model.dto.UpdateIssueRequestDto;
import com.example.repository.IssueRepository;
import com.example.repository.ProjectRepository;
import com.example.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class IssueServiceRdb implements IssueService {

    private static final String ISSUE_NOT_FOUND = "Issue with id %s not found!";
    private static final String PROJECT_NOT_FOUND =  "Project with id %s not found!";
    private static final String USER_NOT_FOUND = "User with id %s not found!";

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;
    private final IssueMapper mapper;

    @Override
    public Page<IssueDto> getAllIssues(Integer page, Integer size) {
        return issueRepository.findAll(PageRequest.of(page, size))
                .map(mapper::fromEntity);
    }

    @Override
    public Page<IssueDto> getAllIssues(Integer page, Integer size, Specification<IssueDto> specification) {
        return issueRepository.findAll(PageRequest.of(page, size),specification).map(mapper::fromEntity);
    }

    @Override
    public IssueDto getOne(Long id) {
        return issueRepository.findById(id)
                .map(mapper::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ISSUE_NOT_FOUND, id)));
    }

    @Override
    public IssueDto createIssue(CreateIssueRequestDto issueDto) {
        var project = projectRepository.findById(issueDto.getProject().getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(PROJECT_NOT_FOUND,
                                issueDto.getProject().getId())
                        ));
        var user = personRepository.findById(issueDto.getPerson().getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(USER_NOT_FOUND,
                                issueDto.getProject().getId())
                        ));
        var issue = mapper.toEntity(issueDto);
        issue.setProject(project);
        issue.setPerson(user);
        issueRepository.save(issue);
        return mapper.fromEntity(issue);
    }

    @Override
    public IssueDto updateIssue(UpdateIssueRequestDto issueDto) {
        var issue = issueRepository.findById(issueDto.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(ISSUE_NOT_FOUND, issueDto.getId()))
                );
        var project = projectRepository.findById(issueDto.getProject().getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(PROJECT_NOT_FOUND,
                                issueDto.getProject().getId())
                        ));
        var user = personRepository.findById(issueDto.getPerson().getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(USER_NOT_FOUND,
                                issueDto.getProject().getId())
                        ));
        issue = mapper.updateEntityFromDto(issueDto, issue);
        issue.setProject(project);
        issue.setPerson(user);
        issueRepository.save(issue);
        return mapper.fromEntity(issue);
    }
}
