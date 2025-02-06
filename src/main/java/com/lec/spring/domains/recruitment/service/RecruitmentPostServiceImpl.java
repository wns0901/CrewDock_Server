package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.entity.ProjectMemberStatus;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentPostServiceImpl implements RecruitmentPostService {

    private final RecruitmentPostRepository postRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    // 전체 페이징 (16개씩)
    @Override
    public Page<RecruitmentPost> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, 16, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pageable);
    }

    // 전체 필터링 페이지
    @Override
    public Page<RecruitmentPost> findByFilters(String stack, String position, String proceedMethod, String region, int page) {
        Pageable pageable = PageRequest.of(page - 1, 16, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findByFilters(stack, position, proceedMethod, region, pageable);
    }

    @Override
    public Page<RecruitmentPost> findClosingRecruitments(int page) {
        LocalDate today = LocalDate.now();
        LocalDate closingDate = today.plusDays(3);

        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(
                Sort.Order.asc("deadline"),  // 마감일 오름차순
                Sort.Order.asc("recruitedNumber") // 잔여 모집 인원 적은 순
        ));

        return postRepository.findClosingRecruitments(closingDate, pageable);
    }

    // 내가 적은 모집글 확인하기
    @Override
    public List<RecruitmentPost> myRecruitmentPost(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    // 모집글 상세 보기
    @Override
    public RecruitmentPost detailRecruitmentPost(Long id) {
        return postRepository.findByIdWithUserAndProject(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다"));
    }

    //글 작성
    @Override
    public RecruitmentPost writeRecruitmentPost(RecruitmentPost post) {
        // 쓰기 권한(로그인)이 되어있는지 확인
        User user = userRepository.findById(post.getUserId().getId())
                .orElseThrow(() -> new EntityNotFoundException("로그인이 되어있지 않습니다"));

        // 프로젝트가 있는지 확인
        Project project = projectRepository.findById(post.getProject().getId())
                .orElseThrow(() -> new EntityNotFoundException("프로젝트가 없습니다"));

        post.setUserId(user);
        post.setProject(project);

        return postRepository.save(post);
    }

    // 수정
    @Override
    public RecruitmentPost updateRecruitmentPost(Long id, RecruitmentPost post) {
        RecruitmentPost existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다"));

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setDeadline(post.getDeadline());
        existingPost.setRegion(post.getRegion());
        existingPost.setProceedMethod(post.getProceedMethod());
        existingPost.setRecruitedNumber(post.getRecruitedNumber());
        existingPost.setRecruitedField(post.getRecruitedField());

        return postRepository.save(existingPost);
    }

    // 삭제
    @Override
    public void deleteRecruitmentPost(Long id) {
        RecruitmentPost post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다"));

        postRepository.delete(post);
    }

    // 모집 신청
    @Transactional
    public void applyToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 중복 지원 방지
        if (projectMemberRepository.existsByProjectAndUser(project, user)) {
            throw new IllegalStateException("이미 신청한 프로젝트입니다.");
        }

        // 프로젝트 멤버 신청 (authority: WAITING, status: REQUEST)
        ProjectMember projectMember = ProjectMember.builder()
                .project(project)  // 프로젝트 ID 반영
                .userId(user)
                .authority(ProjectMemberAuthirity.WAITING)  // 대기 상태
                .status(ProjectMemberStatus.REQUEST)        // 요청 상태
                .position(user.getHopePosition())           // 희망 포지션
                .build();

        projectMemberRepository.save(projectMember);
    }

}
