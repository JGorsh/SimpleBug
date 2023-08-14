package com.example.service;

import com.example.model.domain.Issue;
import com.example.model.dto.CommentDto;
import org.springframework.data.domain.Page;

public interface CommentService {

    Page<CommentDto> getAllComment (Issue issue, Integer page, Integer size);
}
