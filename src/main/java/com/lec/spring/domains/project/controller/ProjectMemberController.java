package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.entity.ProjectMemberStatus;
import com.lec.spring.domains.project.service.ProjectMemberServiceImpl;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberServiceImpl projectMemberServiceImpl;

    // 멤버 조회
    @GetMapping("/{projectId}/members")
    public ResponseEntity<List<ProjectMember>> getProjectMembers(@PathVariable Long projectId) {
        List<Tuple> tuples = projectMemberServiceImpl.findMembersByProjectId(projectId);

        List<ProjectMember> members = tuples.stream()
                .map(tuple -> {
                    ProjectMember projectMember = tuple.get(0, ProjectMember.class);  // ProjectMember 추출
                    String userName = tuple.get(1, String.class);  // User name 추출
                    return projectMember;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }

    // 멤버 탈퇴
    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Void> deleteProjectMember(@PathVariable Long projectId, @PathVariable Long userId) {
        projectMemberServiceImpl.updateMemberStatus(projectId, userId, ProjectMemberStatus.WITHDRAW);
        return ResponseEntity.noContent().build();
    }

    // 권한 변경
    @PatchMapping("/{projectId}/members")
    public ResponseEntity<Void> changeMemberAuthority(
            @PathVariable Long projectId,
            @RequestBody Map<String, Object> request) {

        Long userId = Long.valueOf(String.valueOf(request.get("userId")));  // userId 추출
        String authorityStr = String.valueOf(request.get("authority"));  // authority 추출
        ProjectMemberAuthirity authority = ProjectMemberAuthirity.valueOf(authorityStr);  // Enum 변환

        projectMemberServiceImpl.updateMemberAuthority(projectId, userId, authority);
        return ResponseEntity.noContent().build();
    }
}
