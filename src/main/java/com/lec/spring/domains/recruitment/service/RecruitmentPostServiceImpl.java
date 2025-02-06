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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //
    @Override
    public Page<RecruitmentPost> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
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
        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("로그인이 되어있지 않습니다"));

        // 프로젝트가 있는지 확인
        Project project = projectRepository.findById(post.getProject().getId())
                .orElseThrow(() -> new EntityNotFoundException("프로젝트가 없습니다"));

        post.setUser(user);
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
    public void applyToProject(Long postId, Long userId) {
        RecruitmentPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 중복 지원 방지
        if (projectMemberRepository.existsByProjectAndUser(post.getProject(), user)) {
            throw new IllegalStateException("이미 지원한 모집글입니다.");
        }

        // 프로젝트 멤버 신청 (authority: APPLICANT, status: PENDING)
        Project project = post.getProject();
        ProjectMember projectMember = ProjectMember.builder()
                .project(project)
                .user(user)
                .authority(ProjectMemberAuthirity.WAITING)  // 대기 상태
                .status(ProjectMemberStatus.REQUEST)        // 요청 상태
                .position(user.getHopePosition())           // 희망 포지션
                .build();

        projectMemberRepository.save(projectMember);
    }


}
