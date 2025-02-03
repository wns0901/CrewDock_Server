package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.dto.ProjectDTO;
import com.lec.spring.domains.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;


    @GetMapping("/members")
    public ResponseEntity<List<ProjectDTO>> getUserProjects(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {



        List<ProjectDTO> projects = (row > 0) ?
                projectService.getUserProjectsWithLimitAndStacks(userId, row) :
                projectService.getUserProjectsWithStacks(userId);

        return ResponseEntity.ok(projects);
    }


    @GetMapping("/members/recruitments")
    public ResponseEntity<List<ProjectDTO>> getUserRecruitmentProjects(
            @RequestParam("userId") Long userId) {


        List<ProjectDTO> projects = projectService.getUserRecruitmentProjects(userId);
        return ResponseEntity.ok(projects);
    }
}
