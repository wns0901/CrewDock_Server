package com.lec.spring.domains.project.dto;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectIssueDTO {
    private Long id;
    private String issueName;
    private ProjectIssueStatus status;
    private ProjectIssuePriority priority;
    private LocalDate deadline;
    private LocalDate startline;
    private LocalDateTime createAt;

    // writer와 manager는 User의 정보를 간단히 포함
    private Long writerId;  // 작성자 ID  userId

    private Long managerId; // 담당자 ID  userId
    private String managerName; // 담당자 이름  userName

    // Project 관련 정보
    private Long projectId;

    public static ProjectIssueDTO from(ProjectIssue projectIssue) {
        return ProjectIssueDTO.builder()
                .id(projectIssue.getId())
                .issueName(projectIssue.getIssueName())
                .status(projectIssue.getStatus())
                .priority(projectIssue.getPriority())
                .deadline(projectIssue.getDeadline())
                .startline(projectIssue.getStartline())
                .createAt(projectIssue.getCreateAt())
                .writerId(projectIssue.getWriter().getId())
                .managerId(projectIssue.getManager().getId())
                .managerName(projectIssue.getManager().getName())
                .projectId(projectIssue.getProject().getId())
                .build();
    }
}
