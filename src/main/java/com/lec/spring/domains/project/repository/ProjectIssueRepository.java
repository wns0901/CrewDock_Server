package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.repository.dsl.QProjectIssueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectIssueRepository extends JpaRepository<ProjectIssue, Long>, QProjectIssueRepository {
    List<ProjectIssue> findByProjectId(Long projectId);
}
