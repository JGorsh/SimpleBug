package com.example.repository;

import com.example.model.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment c where c.issue_id = :id", nativeQuery = true)
    Page<Comment> findAllByIssueId (@Param("id")Long issueId , Pageable pageable);
}
