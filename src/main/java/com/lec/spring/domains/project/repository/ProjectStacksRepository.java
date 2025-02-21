package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectStacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectStacksRepository extends JpaRepository<ProjectStacks, Long> {

    void deleteByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProjectStacks ps WHERE ps.projectId = :projectId")
    void deleteByProjectIdWithQuery(@Param("projectId") Long projectId);

    // 특정 기술 스택을 포함하는 프로젝트 ID 리스트 조회 - 필터옵션
    @Query("SELECT DISTINCT ps.projectId FROM ProjectStacks ps WHERE ps.stack.name = :stackName")
    List<Long> findProjectsByStack(@Param("stackName") String stackName);

    List<ProjectStacks> findByProjectId(Long projectId);

    boolean existsByProjectId(Long projectId);
}

