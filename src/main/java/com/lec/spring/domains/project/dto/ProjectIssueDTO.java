package com.lec.spring.domains.project.dto;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.lec.spring.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectIssueDTO{
    // writer와 manager는 User의 정보를 간단히 포함
    private Long writerId;  // 작성자 ID  userId
    private String writerName; // 작성자 이름 userName

    private Long managerId; // 담당자 ID  userId
    private String managerName; // 담당자 이름  userName

    // Project 관련 정보
    private Long projectId;

    private Long issueId;

    private String issueName;   // 이슈 이름(작업명)
    private ProjectIssuePriority priority;  // 우선 순위
    private ProjectIssueStatus status;  // 상태
    private LocalDate deadline; // 마감일
    private LocalDate startline; // 시작일
    private LocalDateTime createAt;    // 작성일


    public ProjectIssueDTO(Long issueId, String issueName, ProjectIssuePriority priority, ProjectIssueStatus status, LocalDate deadline, LocalDate startline, LocalDateTime createAt, Long writerId, String writerName, Long managerId, String managerName, Long projectId) {
        this.issueId = issueId;
        this.issueName = issueName;
        this.status = status;
        this.priority = priority; // issueDTO.getPriority()는 불필요, 이미 파라미터로 넘어옴
        this.deadline = deadline;
        this.startline = startline;
        this.createAt = createAt;

        this.writerId = writerId;
        this.writerName = writerName;
        // Manager가 존재하는지 확인 후 닉네임을 설정
        this.managerId = managerId;  // issueDTO.getManagerName() 대신 managerId 사용
        this.managerName = (managerId != null) ? writerName : null;

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
                .project(project)
                .createAt(LocalDateTime.now())  // 생성 시간 설정
                .build();
    }

}
