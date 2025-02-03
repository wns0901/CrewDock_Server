package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.stack.entity.QStack;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class QProjectRepositoryImpl implements QProjectRepository {

    private final JPAQueryFactory queryFactory;

    private final QProject project = QProject.project;
    private final QProjectMember projectMember = QProjectMember.projectMember;
    private final QProjectStacks projectStacks = QProjectStacks.projectStacks;
    private final QStack stack = QStack.stack;


    public List<Project> findProjectsByUserIdAndAuthorityWithStacksQueryDSL(Long userId, List<String> authorities) {
        List<ProjectMemberAuthirity> authorityList = authorities.stream()
                .map(ProjectMemberAuthirity::valueOf)
                .collect(Collectors.toList());

        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .where(projectMember.userId.eq(userId)
                        .and(projectMember.authority.in(authorityList))) // authority 필터링
                .fetch();
    }

    public List<Project> findProjectsByUserIdAndAuthorities(Long userId, List<String> authorities, int row) {
        List<ProjectMemberAuthirity> authorityList = authorities.stream()
                .map(ProjectMemberAuthirity::valueOf)
                .collect(Collectors.toList());

        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .where(projectMember.userId.eq(userId)
                        .and(projectMember.authority.in(authorityList))) // authority 필터링
                .limit(row) // row 제한
                .fetch();
    }

    @Override
    public List<Project>findRecruitmentProjectsByUserId(Long userId, List<String> authorities) {
        List<ProjectMemberAuthirity> authorityList = authorities.stream()
                .map(ProjectMemberAuthirity::valueOf)
                .collect(Collectors.toList());

        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .where(projectMember.userId.eq(userId)
                        .and(projectMember.authority.in(authorityList))) // authority 필터링
                .fetch();
    }

}
