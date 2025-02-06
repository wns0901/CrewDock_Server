package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM project p JOIN ProjectMember pm ON p.id = pm.project.id WHERE pm.user = :user AND pm.authority = 'CAPTAIN'")
    List<Project> findAllByCaptainUser(@Param("user") User user);


}
