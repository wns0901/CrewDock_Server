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


    public List<Project> findProjectsByUserIdAndAuthorityWithStacksQueryDSL(Long userId) {
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        // PROJECT_로 시작하는 권한 필터링
        List<String> authorities = Arrays.stream(roles.split(","))
                .filter(r -> r.startsWith("PROJECT_"))
                .collect(Collectors.toList());

        List<Long> projectIds = new ArrayList<>();
        List<ProjectMemberAuthirity> projectAuthorities = new ArrayList<>();

        for (String auth : authorities) {
            String[] parts = auth.split("_");
            if (parts.length < 3) continue;

            try {
                Long projectId = Long.parseLong(parts[1]);
                String roleName = parts[2];

                projectIds.add(projectId);
                projectAuthorities.add(ProjectMemberAuthirity.valueOf(roleName));
            } catch (NumberFormatException e) {
                System.err.println("잘못된 권한 포맷: " + auth);
            }
        }

        // CREW 또는 CAPTAIN 권한만 필터링
        List<ProjectMemberAuthirity> filteredAuthorities = projectAuthorities.stream()
                .filter(auth -> auth == ProjectMemberAuthirity.CAPTAIN || auth == ProjectMemberAuthirity.CREW)
                .collect(Collectors.toList());

        // 필터링된 권한이 없으면 아무것도 조회하지 않음
        if (filteredAuthorities.isEmpty()) {
            return new ArrayList<>();
        }

        // QueryDSL
        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id))
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id))
                .where(
                        projectMember.userId.eq(userId)
                                .and(projectMember.project.id.in(projectIds))
                                .and(projectMember.authority.in(filteredAuthorities))
                )
                .fetch();
    }





    public List<Project> findProjectsByUserIdAndAuthorities(Long userId, int row) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        List<String> authorities = Arrays.stream(roles.split(","))
                .filter(r -> r.startsWith("PROJECT_"))
                .collect(Collectors.toList());

        List<Long> projectIds = new ArrayList<>();
        List<ProjectMemberAuthirity> projectAuthorities = new ArrayList<>();

        for (String auth : authorities) {
            String[] parts = auth.split("_");
            if (parts.length < 3) continue;

            try {
                Long projectId = Long.parseLong(parts[1]);
                String roleName = parts[2];

                projectIds.add(projectId);
                projectAuthorities.add(ProjectMemberAuthirity.valueOf(roleName));
            } catch (NumberFormatException e) {
                System.err.println("잘못된 권한 포맷: " + auth);
            }
        }

        // CREW 또는 CAPTAIN만 필터링
        List<ProjectMemberAuthirity> authorityList = projectAuthorities.stream()
                .filter(auth -> auth == ProjectMemberAuthirity.CAPTAIN || auth == ProjectMemberAuthirity.CREW)
                .collect(Collectors.toList());

        // 필터링된 권한이 없으면 아무것도 조회하지 않음
        if (authorityList.isEmpty()) {
            return new ArrayList<>();
        }

        // QueryDSL
        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id))
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id))
                .where(
                        projectMember.userId.eq(userId)
                                .and(projectMember.project.id.in(projectIds))
                                .and(projectMember.authority.in(authorityList))
                )
                .limit(row)
                .fetch();
    }



    @Override
    public List<Project> findRecruitmentProjectsByUserId(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));


        List<String> authorities = Arrays.stream(roles.split(","))
                .filter(r -> r.startsWith("PROJECT_"))
                .collect(Collectors.toList());

        List<Long> projectIds = new ArrayList<>();
        List<ProjectMemberAuthirity> projectAuthorities = new ArrayList<>();

        for (String auth : authorities) {
            String[] parts = auth.split("_");
            if (parts.length < 3) continue;

            try {
                Long projectId = Long.parseLong(parts[1]);
                String role = parts[2];
                projectIds.add(projectId);
                projectAuthorities.add(ProjectMemberAuthirity.valueOf(role));
            } catch (NumberFormatException e) {
                System.err.println("잘못된 권한 포맷: " + auth);
            }
        }


        List<ProjectMemberAuthirity> filteredAuthorities = projectAuthorities.stream()
                .filter(auth -> auth == ProjectMemberAuthirity.CREW || auth == ProjectMemberAuthirity.WAITING)
                .collect(Collectors.toList());

        if (filteredAuthorities.isEmpty()) {
            return new ArrayList<>();  // ✅ 빈 리스트 반환
        }


        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project))
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id))
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id))
                .leftJoin(recruitmentPost).on(recruitmentPost.project.id.eq(project.id))
                .where(
                        projectMember.userId.eq(userId)
                                .and(projectMember.project.id.in(projectIds))
                                .and(projectMember.authority.in(filteredAuthorities))
                )
                .fetch();
    }





}
