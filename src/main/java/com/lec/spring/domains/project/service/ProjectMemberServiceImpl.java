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
                .where(project.id.eq(projectId))
                .fetch();
    }
    // 탈퇴
    @Transactional
    public void updateMemberStatus(Long projectId, Long userId, ProjectMemberStatus status) {
        QProjectMember projectMember = QProjectMember.projectMember;

        // 상태 변경 로직
        queryFactory.update(QProjectMember.projectMember)
                .set(QProjectMember.projectMember.status, status)
                .where(
                        QProjectMember.projectMember.project.id.eq(projectId)
                                .and(QProjectMember.projectMember.userId.eq(userId)) // 수정된 부분
                )
                .execute();
    }


    @Transactional
    public void updateMemberAuthority(Long projectId, Long userId, ProjectMemberAuthirity authority) {
        QProjectMember projectMember = QProjectMember.projectMember;

        long updateSuccess = queryFactory.update(projectMember)
                .set(projectMember.authority, authority)
                .where(projectMember.project.id.eq(projectId)
                        .and(projectMember.userId.eq(userId))) // 수정된 부분
                .execute();

        if (updateSuccess == 0) {
            throw new EntityNotFoundException("존재하지 않은 멤버임");
        }
    }

}

