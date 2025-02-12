package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.entity.ProjectMemberStatus;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentPostDTO;
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

import static com.lec.spring.domains.stack.entity.QStack.stack;
import static com.lec.spring.domains.user.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentPostServiceImpl implements RecruitmentPostService {

    private final RecruitmentPostRepository postRepository;

    //TODO:
  //  @Qualifier("qRecruitmentPostRepository") // ✅ QueryDSL Repository 명확히 지정
    private final QRecruitmentPostRepository qRecruitmentPostRepository;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public Page<RecruitmentPostDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, 16, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<RecruitmentPost> posts = postRepository.findAllRecruitments(pageable); // ✅ 커스텀 쿼리 사용
        return posts.map(RecruitmentPostDTO::fromEntity);
    }

    // 모집글 필터 조회 (QueryDSL 사용)
    @Override
    public Page<RecruitmentPostDTO> findByFilters(String stack, String position, String proceedMethod, String region, Pageable pageable) {
        return qRecruitmentPostRepository.findByFilters(stack, position, proceedMethod, region, pageable);
    }

    // 마감 임박 모집글 조회
    @Override
    public Page<RecruitmentPostDTO> findClosingRecruitments(int page) {
//        LocalDate closingDate = LocalDate.now().plusDays(3);
//        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Sort.Order.asc("deadline"), Sort.Order.asc("recruitedNumber")));
//        return qRecruitmentPostRepository.findByFilters(stack, position, proceedMethod, region, pageable);
        return null;
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
    @Transactional
    public RecruitmentPost updateRecruitmentPost(Long id, RecruitmentPost post) {
        RecruitmentPost existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다: " + id));

        // null이 아닐 때만 업데이트 (입력되지 않은 필드는 기존 값 유지)
        if (post.getTitle() != null) existingPost.setTitle(post.getTitle());
        if (post.getContent() != null) existingPost.setContent(post.getContent());
        if (post.getDeadline() != null) existingPost.setDeadline(post.getDeadline());
        if (post.getRegion() != null) existingPost.setRegion(post.getRegion());
        if (post.getProceedMethod() != null) existingPost.setProceedMethod(post.getProceedMethod());
        if (post.getRecruitedNumber() != null) existingPost.setRecruitedNumber(post.getRecruitedNumber());
        if (post.getRecruitedField() != null) existingPost.setRecruitedField(post.getRecruitedField());

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
