package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.repository.dsl.QProjectMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long>, QProjectMemberRepository {
}
