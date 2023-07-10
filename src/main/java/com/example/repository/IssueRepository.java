package com.example.repository;

import com.example.model.domain.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IssueRepository extends JpaRepository<Issue, Long> {

    @EntityGraph(attributePaths = {"project", "person"})
    @Override
    Page<Issue> findAll(Pageable pageable);

}
