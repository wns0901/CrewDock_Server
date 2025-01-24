package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectIssueRepository extends JpaRepository<ProjectIssue, Long> {
}
