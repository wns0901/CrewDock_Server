package com.lec.spring.domains.project.dto;

import com.lec.spring.domains.project.entity.ProjectStacks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectStacksDTO {

    private Long id;
    private Long projectId;
    private String stackName; // StackDTO로 변환

    // ProjectStacks 엔티티를 ProjectStacksDTO로 변환
    public ProjectStacksDTO(ProjectStacks projectStacks) {
        this.id = projectStacks.getId();
        this.projectId = projectStacks.getProjectId();
        this.stackName = projectStacks.getStack().getName();  // Stack의 name을 가져옴
    }
}
