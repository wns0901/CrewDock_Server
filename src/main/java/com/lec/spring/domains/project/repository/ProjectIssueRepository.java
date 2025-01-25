package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectIssueRepository extends JpaRepository<ProjectIssue, Long> {
    List<ProjectIssue> findByProjectId(Long projectId);
}
