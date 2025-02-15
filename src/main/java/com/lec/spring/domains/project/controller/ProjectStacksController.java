package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.dto.ProjectStacksDTO;
import com.lec.spring.domains.project.entity.ProjectStacks;
import com.lec.spring.domains.project.service.ProjectStacksService;
import com.lec.spring.domains.project.service.ProjectStacksServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects") // ✅ 이 부분이 올바른지 확인
@RequiredArgsConstructor
public class ProjectStacksController {

    private final ProjectStacksService projectStacksService;

    @GetMapping("/{projectId}/stacks")  // ✅ 프로젝트 ID 기반 스택 조회
    public ResponseEntity<?> getProjectStacksByProjectId(@PathVariable Long projectId) {
        if (!projectStacksService.existsByProjectId(projectId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 프로젝트가 존재하지 않습니다.");
        }

        List<ProjectStacksDTO> stackList = projectStacksService.findByProjectId(projectId)
                .stream()
                .map(ProjectStacksDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(stackList);
    }
}
