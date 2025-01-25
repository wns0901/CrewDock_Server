package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.service.ProjectMemberServiceImpl;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberServiceImpl projectMemberServiceImpl;

    @GetMapping("/{projectId}/members")
    public ResponseEntity<List<ProjectMember>> getProjectMembers(@PathVariable Long projectId) {
        List<Tuple> tuples = projectMemberServiceImpl.findMembersByProjectId(projectId);

        List<ProjectMember> members = tuples.stream()
                .map(tuple -> {
                    ProjectMember projectMember = tuple.get(0, ProjectMember.class);  // ProjectMember 객체 추출
                    String userName = tuple.get(1, String.class);  // User name 추출
                    return projectMember;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }
}
