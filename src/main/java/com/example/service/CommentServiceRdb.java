package com.example.service;

import com.example.mapping.CommentMapper;
import com.example.model.domain.Issue;
import com.example.model.dto.CommentDto;
import com.example.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceRdb implements CommentService{

    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    @Override
    public Page<CommentDto> getAllComment(Issue issue, Integer page, Integer size) {
        return commentRepository.findAllByIssue(issue, PageRequest.of(page, size)).map(mapper::fromEntity);
    }
}
