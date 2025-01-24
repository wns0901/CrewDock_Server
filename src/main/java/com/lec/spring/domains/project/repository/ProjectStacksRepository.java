package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectStacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStacksRepository extends JpaRepository<ProjectStacks, Long> {
}
