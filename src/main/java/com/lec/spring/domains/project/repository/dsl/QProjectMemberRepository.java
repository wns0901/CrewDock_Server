package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.entity.ProjectMember;

public interface QProjectMemberRepository {
    ProjectMember findProjectCaptain(Long projectId);
}
