package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.entity.ProjectMemberStatus;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentPostRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.lec.spring.domains.user.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentPostServiceImpl implements RecruitmentPostService {

    private final RecruitmentPostRepository postRepository;

    //TODO:
    @Qualifier("qRecruitmentPostRepository") // ✅ QueryDSL Repository 명확히 지정
    private final QRecruitmentPostRepository qRecruitmentPostRepository;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    // 모집글 전체 조회
    @Override
    public Page<RecruitmentPost> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, 16, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pageable);
    }

    // 모집글 필터 조회 (QueryDSL 사용)
    @Override
    public Page<RecruitmentPost> findByFilters(String stack, String position, String proceedMethod, String region, int page) {
        Pageable pageable = PageRequest.of(page - 1, 16, Sort.by(Sort.Direction.DESC, "createdAt"));
        return qRecruitmentPostRepository.findByFilters(stack, position, proceedMethod, region, pageable);
    }

    // 마감 임박 모집글 조회
    @Override
    public Page<RecruitmentPost> findClosingRecruitments(int page) {
        LocalDate closingDate = LocalDate.now().plusDays(3);
        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Sort.Order.asc("deadline"), Sort.Order.asc("recruitedNumber")));
        return qRecruitmentPostRepository.findClosingRecruitments(closingDate, pageable);
    }
    //TODO: 조회는 하나 제대로 안됨.

    // 내가 작성한 모집글 조회
    @Override
    public List<RecruitmentPost> myRecruitmentPost(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    // 모집글 상세 조회
    @Override
    public RecruitmentPost detailRecruitmentPost(Long id) {
        return qRecruitmentPostRepository.findByIdWithUserAndProject(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다"));
    }

    // 모집글 작성
    @Override
    @Transactional
    public RecruitmentPost writeRecruitmentPost(RecruitmentPost post) {
        if (post.getUser() == null || post.getUser().getId() == null) {
            throw new IllegalArgumentException("유저 정보가 없습니다. JSON 형식을 확인하세요.");
        }

        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
        Project project = projectRepository.findById(post.getProject().getId())
                .orElseThrow(() -> new EntityNotFoundException("프로젝트가 존재하지 않습니다"));

        post.setUser(user);
        post.setProject(project);

        return postRepository.save(post);
    }

    // 모집글 수정
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

    // 모집글 삭제
    @Override
    public void deleteRecruitmentPost(Long id) {
        RecruitmentPost post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다"));
        postRepository.delete(post);
    }

    // 모집 지원
    @Transactional
    public void applyToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (projectMemberRepository.existsByProjectAndUserId(project, user)) {
            throw new IllegalStateException("이미 지원한 프로젝트입니다.");
        }

        ProjectMember projectMember = ProjectMember.builder()
                .project(project)
                .userId(user.getId())
                .authority(ProjectMemberAuthirity.WAITING)
                .status(ProjectMemberStatus.REQUEST)
                .position(user.getHopePosition())
                .build();

        projectMemberRepository.save(projectMember);
    }
}
