package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.service.ProjectIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectIssueController {

    private final ProjectIssueService projectIssueService;

    // 이슈 목록 get "/projects/{projectId}/issues"
    @GetMapping("/{projectId}/issues")
    public ResponseEntity<List<ProjectIssueDTO>> getIssues(@PathVariable Long projectId) {
        List<ProjectIssueDTO> issues = projectIssueService.listByProjectId(projectId);
        return ResponseEntity.ok(issues);
    }


    // 이슈 작성 post "/projects/{projectId}/issues"
    @PostMapping("/{projectId}/issues")
    public ResponseEntity<ProjectIssue> createIssue(
            @PathVariable Long projectId,
            @RequestBody ProjectIssueDTO projectIssueDTO) {
        ProjectIssue savedIssue = projectIssueService.save(projectId, projectIssueDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIssue);
    }

    // 이슈 상세보기 get "/projects/{projectId}/issues/{issueId}
    @GetMapping("/{projectId}/issues/{issueId}")
    public ResponseEntity<ProjectIssueDTO> getIssueDetail(
            @PathVariable Long projectId,
            @PathVariable Long issueId
    ) {
        ProjectIssueDTO issueDTO = projectIssueService.getIssueDetail(projectId, issueId);
        return ResponseEntity.ok(issueDTO);
    }

    // 이슈 수정 patch "/projects/{projectId}/issues"
    @PatchMapping("/{projectId}/issues")
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

    @DeleteMapping("/{projectId}/issues")
    public ResponseEntity<Void> deleteIssues(
            @PathVariable Long projectId,
            @RequestParam List<Long> issueIds) {

        // 다중 삭제 처리
        if (issueIds != null && !issueIds.isEmpty()) {
            int deletedCount = projectIssueService.deleteByIds(issueIds);
            if (deletedCount > 0) {
                return ResponseEntity.noContent().build(); // 성공적으로 삭제
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 삭제 실패
    }

    @DeleteMapping("/{projectId}/issues/{issueId}")
    public ResponseEntity<Void> deleteIssue(
            @PathVariable Long projectId,
            @PathVariable Long issueId) {

        // 개별 삭제 처리
        int deletedCount = projectIssueService.deleteById(issueId);
        if (deletedCount > 0) {
            return ResponseEntity.noContent().build(); // 성공적으로 삭제
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 삭제 실패
    }

}
