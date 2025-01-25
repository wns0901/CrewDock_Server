package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.service.ProjectIssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectIssueController {

    private final ProjectIssueService projectIssueService;
    public ProjectIssueController(ProjectIssueService projectIssueService) {
        System.out.println("ProjectIssueController() 생성");
        this.projectIssueService = projectIssueService;
    }

    // 이슈 목록 get "/projects/{projectId}/issues"
    @GetMapping("/{projectId}/issues")
    public ResponseEntity<List<ProjectIssue>> getIssues(@PathVariable Long projectId) {
        List<ProjectIssue> issues = projectIssueService.listByProjectId(projectId);
        return ResponseEntity.ok(issues);
    }


    // 이슈 작성 post "/projects/{projectId}/issues"
    @PostMapping("/{projectId}/issues")
    public ResponseEntity<ProjectIssue> createIssue(
            @PathVariable Long projectId,
            @RequestBody ProjectIssue projectIssue) {
        ProjectIssue savedIssue = projectIssueService.save(projectId, projectIssue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIssue);
    }

    // 이슈 수정 patch "/projects/{projectId}/issues"
    @PatchMapping("/{projectId}/issues/{issueId}")
    public ResponseEntity<Void> updateIssue(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @RequestBody ProjectIssue updatedIssue) {
        int rowsUpdated = projectIssueService.update(projectId, issueId, updatedIssue);

        if (rowsUpdated > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 이슈 삭제 delete "/projects/{projectId}/issues/{projectIssueId}"
    @DeleteMapping("/{projectId}/issues/{issueId}")
    public ResponseEntity<Void> deleteIssue(
            @PathVariable Long projectId,
            @PathVariable Long issueId) {
        int rowsDeleted = projectIssueService.deleteById(issueId);

        if (rowsDeleted > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
