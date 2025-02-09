package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.dto.ProjectCreatDTO;
import com.lec.spring.domains.project.dto.ProjectDTO;
import com.lec.spring.domains.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;


    @GetMapping("/members")
    public ResponseEntity<List<ProjectDTO>> getUserProjects(
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {

        List<ProjectDTO> projects = (row > 0) ?
                projectService.getUserProjectsWithLimitAndStacks(row) :
                projectService.getUserProjectsWithStacks();

        return ResponseEntity.ok(projects);
    }


    @GetMapping("/members/recruitments")
    public ResponseEntity<List<ProjectDTO>> getUserRecruitmentProjects() {


        List<ProjectDTO> projects = projectService.getUserRecruitmentProjects();
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ProjectDTO> createProject(
            @PathVariable Long userId,
            @RequestBody ProjectCreatDTO projectCreatDTO) {
        ProjectDTO createdProject = projectService.createProject(userId, projectCreatDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);

    }



}

