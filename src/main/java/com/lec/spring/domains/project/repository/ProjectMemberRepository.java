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

    boolean existsByProjectAndUserId(Project project, User userId);
    //모집글 포스트 서비스에 사용됩니다
}
