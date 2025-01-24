package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
