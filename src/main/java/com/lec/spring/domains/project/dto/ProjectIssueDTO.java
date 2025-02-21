package com.lec.spring.domains.project.dto;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.lec.spring.domains.user.entity.User;
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
public class ProjectIssueDTO{
    private Long id;
    // writer와 manager는 User의 정보를 간단히 포함
    private Long writerId;  // 작성자 ID  userId
    private String writerName; // 작성자 이름 userName

    private Long managerId; // 담당자 ID  userId
    private String managerName; // 담당자 이름  userName

    // Project 관련 정보
    private Long projectId;

    private String issueName;   // 이슈 이름(작업명)
    private ProjectIssuePriority priority;  // 우선 순위
    private ProjectIssueStatus status;  // 상태
    private LocalDate deadline; // 마감일
    private LocalDate startline; // 시작일
    private LocalDateTime createAt;    // 작성일


    public ProjectIssueDTO(Long id, String issueName, ProjectIssueStatus status, ProjectIssuePriority priority,
                           LocalDate deadline, LocalDate startline, LocalDateTime createAt, Long writerId,
                           String writerName, Long managerId, String managerName, Long projectId) {
        this.id = id;
        this.issueName = issueName;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.startline = startline;
        this.createAt = createAt;
        this.writerId = writerId;
        this.writerName = writerName;
        this.managerId = managerId;
        this.managerName = managerName;
        this.projectId = projectId;
    }

    // DTO에서 엔티티로 변환
    public ProjectIssue toEntity(Project project, User writer, User manager) {
        return ProjectIssue.builder()
                .issueName(this.issueName)
                .status(this.status)
                .priority(this.priority)
                .deadline(this.deadline)
                .startline(this.startline)
                .writer(writer)
                .manager(manager)
                .project(project)// 생성 시간 설정
                .build();
    }

    // entity -> DTO 로 변경
    public static ProjectIssueDTO fromEntity(ProjectIssue projectIssue) {
        return ProjectIssueDTO.builder()
                .id(projectIssue.getId())
                .issueName(projectIssue.getIssueName())
                .status(projectIssue.getStatus())
                .priority(projectIssue.getPriority())
                .deadline(projectIssue.getDeadline())
                .startline(projectIssue.getStartline())
                .writerId(projectIssue.getWriter().getId())
                .writerName(projectIssue.getWriter().getNickname())
                .managerId(projectIssue.getManager().getId())
                .managerName(projectIssue.getManager().getNickname())
                .projectId(projectIssue.getProject().getId())
                .createAt(projectIssue.getCreatedAt())
                .build();
    }

}
