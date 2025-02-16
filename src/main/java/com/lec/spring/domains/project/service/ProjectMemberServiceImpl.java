package com.lec.spring.domains.project.service;

import com.lec.spring.domains.chat.service.ChatRoomService;
import com.lec.spring.domains.project.dto.ProjectMemberDTO;
import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.user.entity.QUser;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl {
    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;
    private final ChatRoomService chatRoomService;

    /**
     * 프로젝트 ID를 기반으로 팀원 목록 조회 (DTO 변환)
     */
    public List<ProjectMemberDTO> findMembersByProjectId(Long projectId) {
        QProjectMember projectMember = QProjectMember.projectMember;
        QUser user = QUser.user;

        // QueryDSL로 프로젝트 멤버와 해당 user 정보 가져오기
        List<ProjectMember> projectMembers = queryFactory
                .selectFrom(projectMember)
                .where(projectMember.project.id.eq(projectId))
                .fetch();

        // userId를 기반으로 User 정보 조회 후 DTO 변환
        return projectMembers.stream()
                .map(member -> {
                    User userEntity = userRepository.findById(member.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("유저 정보를 찾을 수 없습니다."));
                    return ProjectMemberDTO.fromEntity(member, userEntity);
                })
                .collect(Collectors.toList());
    }

    /**
     * 프로젝트 멤버 상태 업데이트 (탈퇴 처리)
     */
    @Transactional
    public void updateMemberStatus(Long projectId, Long userId, ProjectMemberStatus status) {
        QProjectMember projectMember = QProjectMember.projectMember;

        long updateSuccess = queryFactory.update(projectMember)
                .set(projectMember.status, status)
                .where(
                        projectMember.project.id.eq(projectId)
                                .and(projectMember.userId.eq(userId))  // userId를 기준으로 업데이트
                )
                .execute();

        if (updateSuccess == 0) {
            throw new EntityNotFoundException("상태 업데이트에 실패했습니다.");
        }
    }

    /**
     * 프로젝트 멤버 권한 및 상태 업데이트
     */
    @Transactional
    public void updateMemberAuthorityAndStatus(Long projectId, Long userId,
                                               ProjectMemberAuthirity newAuthority, ProjectMemberStatus newStatus) {
        QProjectMember projectMember = QProjectMember.projectMember;

        // 현재 사용자 데이터 조회
        ProjectMember existingMember = queryFactory
                .selectFrom(projectMember)
                .where(projectMember.project.id.eq(projectId)
                        .and(projectMember.userId.eq(userId))) // userId 기준으로 멤버 조회
                .fetchOne();

        if (existingMember == null) {
            throw new EntityNotFoundException("존재하지 않는 멤버입니다.");
        }
        // 전달된 값이 있으면 업데이트, 없으면 기존 값 유지
        ProjectMemberAuthirity updatedAuthority = (newAuthority != null) ? newAuthority : existingMember.getAuthority();
        ProjectMemberStatus updatedStatus = (newStatus != null) ? newStatus : existingMember.getStatus();

        if(existingMember.getStatus() == ProjectMemberStatus.REQUEST && newStatus == ProjectMemberStatus.APPROVE) {
            chatRoomService.inviteUser(existingMember.getProject(), userRepository.findById(userId).orElse(null));
        }

        long updateSuccess = queryFactory.update(projectMember)
                .set(projectMember.authority, updatedAuthority)
                .set(projectMember.status, updatedStatus)
                .where(projectMember.project.id.eq(projectId)
                        .and(projectMember.userId.eq(userId))) // userId 기준으로 멤버 업데이트
                .execute();

        if (updateSuccess == 0) {
            throw new EntityNotFoundException("업데이트에 실패했습니다.");
        }
    }
}
