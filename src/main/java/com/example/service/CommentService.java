package com.example.service;

import com.example.model.domain.Issue;
import com.example.model.dto.CommentDto;
import com.example.model.dto.IssueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<CommentDto> getAllComment (Long issueId, Pageable pageable);
}
