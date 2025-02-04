package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.entity.QProjectMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QProjectMemberRepositoryImpl implements QProjectMemberRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ProjectMember findProjectCaptain(Long projectId) {
        QProjectMember projectMember = QProjectMember.projectMember;

        return queryFactory.selectFrom(projectMember)
                    .where(projectMember.project.id.eq(projectId)
                            .and(projectMember.authority.eq(ProjectMemberAuthirity.CAPTAIN)))
                    .fetchOne();
    }
}
