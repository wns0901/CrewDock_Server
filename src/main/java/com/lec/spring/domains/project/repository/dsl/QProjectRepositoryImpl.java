package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.recruitment.entity.QRecruitmentPost;
import com.lec.spring.domains.stack.entity.QStack;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final QRecruitmentPost recruitmentPost = QRecruitmentPost.recruitmentPost;


    @Override
    public List<Project> findProjectsByUserIdAndAuthorities(Long userId, List<Long> projectIds, List<ProjectMemberAuthirity> authorities, int row) {
        if (authorities.isEmpty() || projectIds.isEmpty()) {
            return List.of();
        }

        long totalProjects = queryFactory
                .select(project.countDistinct())  // ✅ 중복 제거한 개수 확인
                .from(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .where(
                        projectMember.userId.eq(userId)
                                .and(projectMember.project.id.in(projectIds))
                                .and(projectMember.authority.in(authorities))
                )
                .fetchOne();

        System.out.println("필터링 후 총 프로젝트 개수: " + totalProjects);

        List<Project> results = queryFactory
                .selectDistinct(project)  // ✅ 중복된 프로젝트를 제거
                .from(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id))
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id))
                .where(
                        projectMember.userId.eq(userId)
                                .and(projectMember.project.id.in(projectIds))
                                .and(projectMember.authority.in(authorities))
                )
                .orderBy(project.startDate.desc()) // 최신 프로젝트부터 정렬
                .limit(row > 0 ? row : Long.MAX_VALUE) // row 값이 0이면 전체 조회
                .fetch();

        System.out.println("최종 반환된 프로젝트 개수: " + results.size());
        return results;
    }






    @Override
    public List<Project> findRecruitmentProjectsByUserId(Long userId) {
        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id))
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id))
                .leftJoin(recruitmentPost).on(recruitmentPost.project.id.eq(project.id))
                .where(
                        projectMember.userId.eq(userId)
                                .and(projectMember.authority.in(ProjectMemberAuthirity.CREW, ProjectMemberAuthirity.WAITING))
                )
                .fetch();
    }
}
