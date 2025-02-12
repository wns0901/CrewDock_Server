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
    public ResponseEntity<ProjectIssueDTO> createIssue(
            @PathVariable Long projectId,
            @RequestBody ProjectIssueDTO projectIssueDTO) {
        ProjectIssueDTO savedIssue = projectIssueService.save(projectId, projectIssueDTO);
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

    // 이슈 수정 patch "/projects/{projectId}/issues/{issueId}"
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

    // 다중 삭제
    @DeleteMapping("/{projectId}/issues")
    public ResponseEntity<Void> deleteIssues(
            @PathVariable Long projectId,
            @RequestParam List<Long> issueIds) {

        // 다중 삭제 처리
        if (issueIds != null && !issueIds.isEmpty()) {
            int deletedCount = projectIssueService.deleteByIds(projectId, issueIds);
            if (deletedCount > 0) {
                return ResponseEntity.noContent().build(); // 성공적으로 삭[
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 삭제 실패
    }

    // 개별 삭제
    @DeleteMapping("/{projectId}/issues/{issueId}")
    public ResponseEntity<Void> deleteIssue(
            @PathVariable Long projectId,
            @PathVariable Long issueId) {

        try {
            projectIssueService.deleteById(projectId, issueId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
