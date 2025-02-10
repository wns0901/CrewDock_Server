package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.dsl.QProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, QProjectRepository {
}
