package com.example.repository;

import com.example.model.domain.Comment;
import com.example.model.domain.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByIssue (Issue issue , Pageable pageable);
}
