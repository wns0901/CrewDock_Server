package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.repository.dsl.QProjectMemberRepository;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long>, QProjectMemberRepository {
    List<ProjectMember> findByUserId(Long userId);

    ProjectMember findByProjectAndUserId(Project project, Long loggedInUserId);
}
