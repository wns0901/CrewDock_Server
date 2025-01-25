package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.QProject;
import com.lec.spring.domains.project.entity.QProjectMember;
import com.lec.spring.domains.user.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl {
    private final JPAQueryFactory queryFactory;

    public List<Tuple> findMembersByProjectId(Long projectId) {
        QProjectMember projectMember = QProjectMember.projectMember;
        QProject project = QProject.project;
        QUser user = QUser.user;
        return queryFactory
                .select(projectMember, user.name)
                .from(projectMember)
                .join(projectMember.project, project).fetchJoin()
                .join(projectMember.user, user)
                .where(project.id.eq(projectId))
                .fetch();
    }
}
