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
        String role = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        // role을 "," 기준으로 split하여 권한 목록 추출
        List<String> authorities = Arrays.stream(role.split(","))
                .filter(r -> r.startsWith("PROJECT_"))  // PROJECT_로 시작하는 권한만 필터링
                .collect(Collectors.toList());

        List<Long> projectIds = new ArrayList<>();
        List<ProjectMemberAuthirity> projectAuthorities = new ArrayList<>();

        // forEach 대신 일반 for loop를 사용하여 list에 직접 추가
        for (String auth : authorities) {
            String[] parts = auth.split("_");  // PROJECT_1_CAPTAIN -> [PROJECT, 1, CAPTAIN]
            Long projectId = Long.parseLong(parts[1]);  // 프로젝트 ID 부분 (1)
            String roleName = parts[2];  // 권한 부분 (CAPTAIN, CREW)
            projectIds.add(projectId);
            projectAuthorities.add(ProjectMemberAuthirity.valueOf(roleName));  // Enum으로 변환
        }

        // QueryDSL 쿼리
        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project)) // 프로젝트와 프로젝트 멤버 조인
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id)) // ProjectStacks와 조인
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id)) // Stack과 조인
                .where(
                        projectMember.userId.eq(userId)  // userId로 필터링
                                .and(projectMember.project.id.in(projectIds))  // 프로젝트 ID로 필터링
                                .and(projectMember.authority.in(projectAuthorities))  // 권한으로 필터링
                )
                .fetch();  // 결과 반환
    }




    public List<Project> findProjectsByUserIdAndAuthorities(Long userId, int row) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        String roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));


        List<String> authorities = Arrays.stream(roles.split(","))
                .filter(r -> r.startsWith("PROJECT_"))  // PROJECT_로 시작하는 권한만 필터링
                .collect(Collectors.toList());


        List<ProjectMemberAuthirity> authorityList = authorities.stream()
                .map(auth -> {
                    String[] parts = auth.split("_");  // PROJECT_1_CAPTAIN -> [PROJECT, 1, CAPTAIN]
                    String role = parts[2];  // CAPTAIN, CREW 등 권한 부분
                    return ProjectMemberAuthirity.valueOf(role);  // ProjectMemberAuthirity enum으로 변환
                })
                .filter(auth -> auth == ProjectMemberAuthirity.CAPTAIN || auth == ProjectMemberAuthirity.CREW)
                .collect(Collectors.toList());

        // QueryDSL 쿼리
        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project)) // ProjectMember와 조인
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id)) // ProjectStacks와 조인
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id)) // Stack과 조인
                .where(projectMember.userId.eq(userId)
                        .and(projectMember.authority.in(authorityList))) // authority 필터링
                .limit(row)
                .fetch();
    }


    @Override
    public List<Project> findRecruitmentProjectsByUserId(Long userId) {
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        // role을 "," 기준으로 split하여 권한 목록 추출
        List<String> authorities = Arrays.stream(role.split(","))
                .filter(r -> r.startsWith("PROJECT_"))  // PROJECT_로 시작하는 권한만 필터링
                .collect(Collectors.toList());

        // 프로젝트 ID와 권한을 분리
        List<Long> projectIds = new ArrayList<>();
        List<ProjectMemberAuthirity> projectAuthorities = new ArrayList<>();

        authorities.forEach(auth -> {
            String[] parts = auth.split("_");  // PROJECT_1_CREW -> [PROJECT, 1, CREW]
            Long projectId = Long.parseLong(parts[1]);  // 프로젝트 ID 부분 (1)
            String roleName = parts[2];  // 권한 부분 (CREW, WAITING)
            projectIds.add(projectId);
            projectAuthorities.add(ProjectMemberAuthirity.valueOf(roleName));  // Enum으로 변환
        });

        // CREW, WAITING만 필터링
        List<ProjectMemberAuthirity> filteredAuthorities = projectAuthorities.stream()
                .filter(auth -> auth == ProjectMemberAuthirity.CREW || auth == ProjectMemberAuthirity.WAITING) // CREW와 WAITING만 필터링
                .collect(Collectors.toList());

        // 프로젝트 권한에 따라 조회
        return queryFactory
                .selectFrom(project)
                .join(projectMember).on(projectMember.project.eq(project)) // 프로젝트와 프로젝트 멤버 조인
                .leftJoin(projectStacks).on(projectStacks.projectId.eq(project.id)) // ProjectStacks와 조인
                .leftJoin(stack).on(stack.id.eq(projectStacks.stack.id)) // Stack과 조인
                .leftJoin(recruitmentPost).on(recruitmentPost.project.id.eq(project.id)) // RecruitmentPost와 조인
                .where(
                        projectMember.userId.eq(userId)  // userId로 필터링
                                .and(projectMember.project.id.in(projectIds))  // 프로젝트 ID로 필터링
                                .and(projectMember.authority.in(filteredAuthorities))  // CREW와 WAITING 권한만 필터링
                )
                .fetch();  // 결과 반환
    }




}
