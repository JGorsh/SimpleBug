package com.example.repository;

import com.example.model.domain.Issue;
import com.example.model.dto.IssueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    @EntityGraph(attributePaths = {"project", "person"})
    @Override
    Page<Issue> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"project", "person"})
    Page<Issue> findAll (Pageable pageable, Specification<IssueDto> specification);
}
