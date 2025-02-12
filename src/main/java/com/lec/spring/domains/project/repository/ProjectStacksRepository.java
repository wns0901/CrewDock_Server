package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ProjectStacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectStacksRepository extends JpaRepository<ProjectStacks, Long> {

    void deleteByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProjectStacks ps WHERE ps.projectId = :projectId")
    void deleteByProjectIdWithQuery(@Param("projectId") Long projectId);
}

