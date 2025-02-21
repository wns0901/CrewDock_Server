package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.repository.dsl.QProjectIssueRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProjectIssueRepository extends JpaRepository<ProjectIssue, Long>, QProjectIssueRepository{
    long countByIdIn(List<Long> ids);
}
