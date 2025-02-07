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
public class ProjectIssueDTO extends ProjectIssue{
    // writer와 manager는 User의 정보를 간단히 포함
    private Long writerId;  // 작성자 ID  userId

    private Long managerId; // 담당자 ID  userId
    private String managerName; // 담당자 이름  userName

    // Project 관련 정보
    private Long projectId;

}
