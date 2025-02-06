package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.user.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    // 탈퇴
    @Transactional
    public void updateMemberStatus(Long projectId, Long userId, ProjectMemberStatus status) {
        QProjectMember projectMember = QProjectMember.projectMember;

        // 상태 변경 로직
        queryFactory.update(projectMember)
                .set(projectMember.status, status)
                .where(
                        projectMember.project.id.eq(projectId)
                                .and(projectMember.user.id.eq(userId))
                )
                .execute();
    }
    @Transactional
    public void updateMemberAuthorityAndStatus(Long projectId, Long userId,
                                               ProjectMemberAuthirity newAuthority, ProjectMemberStatus newStatus) {
        QProjectMember projectMember = QProjectMember.projectMember;

        // 현재 사용자 데이터 조회
        ProjectMember existingMember = queryFactory
                .selectFrom(projectMember)
                .where(projectMember.project.id.eq(projectId)
                        .and(projectMember.user.id.eq(userId)))
                .fetchOne();

        if (existingMember == null) {
            throw new EntityNotFoundException("존재하지 않는 멤버입니다.");
        }

        // 전달된 값이 있으면 업데이트, 없으면 기존 값 유지
        ProjectMemberAuthirity updatedAuthority = (newAuthority != null) ? newAuthority : existingMember.getAuthority();
        ProjectMemberStatus updatedStatus = (newStatus != null) ? newStatus : existingMember.getStatus();

        // QueryDSL을 사용하여 authority, status 업데이트
        long updateSuccess = queryFactory.update(projectMember)
                .set(projectMember.authority, updatedAuthority)
                .set(projectMember.status, updatedStatus)
                .where(projectMember.project.id.eq(projectId)
                        .and(projectMember.user.id.eq(userId)))
                .execute();

        if (updateSuccess == 0) {
            throw new EntityNotFoundException("업데이트에 실패했습니다.");
        }
    }
}
