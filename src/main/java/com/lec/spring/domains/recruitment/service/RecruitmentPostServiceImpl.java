package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.entity.ProjectMemberStatus;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.project.service.ProjectStacksService;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentPostDTO;
import com.lec.spring.domains.recruitment.entity.ProceedMethod;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.Region;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentPostRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentPostServiceImpl implements RecruitmentPostService {

    private final RecruitmentPostRepository postRepository;
    private final QRecruitmentPostRepository qRecruitmentPostRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectStacksService projectStacksService;

    // ✅ 모든 모집글 조회
    @Override
    public Page<RecruitmentPostDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, 16, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<RecruitmentPost> posts = postRepository.findAllRecruitments(pageable);
        return posts.map(RecruitmentPostDTO::fromEntity);
    }

    // ✅ 모집글 필터 조회 (QueryDSL + 프로젝트 스택 필터 적용)
    @Override
    public Page<RecruitmentPostDTO> findByFilters(String stack, String position, String proceedMethod, String region, Pageable pageable) {
        return qRecruitmentPostRepository.findByFilters(stack, position, proceedMethod, region, pageable);
    }

    // ✅ 마감 임박 모집글 조회
    @Override
    public Page<RecruitmentPostDTO> findClosingRecruitments(int page) {
        LocalDate closingDate = LocalDate.now().plusDays(3);
        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Sort.Order.asc("deadline"), Sort.Order.asc("recruitedNumber")));
        return qRecruitmentPostRepository.findClosingRecruitments(closingDate, pageable);
    }

    // ✅ 내가 작성한 모집글 조회
    @Override
    public List<RecruitmentPost> myRecruitmentPost(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    // ✅ 모집글 상세 조회 (불필요한 stackList 조회 제거)
    @Override
    public RecruitmentPostDTO detailRecruitmentPost(Long id) {
        RecruitmentPost post = qRecruitmentPostRepository.findByIdWithUserAndProject(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다"));

        return RecruitmentPostDTO.fromEntity(post);
    }

    // ✅ 모집글 작성
    // ✅ 모집글 작성
    @Override
    @Transactional
    public RecruitmentPost writeRecruitmentPost(RecruitmentPostDTO postDTO) {
        if (postDTO.getUser() == null || postDTO.getUser().getUserId() == null) {
            throw new IllegalArgumentException("유저 정보가 없습니다. JSON 형식을 확인하세요.");
        }

        // 유저 조회
        User user = userRepository.findById(postDTO.getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        // 프로젝트 조회
        Project project = projectRepository.findById(postDTO.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("프로젝트가 존재하지 않습니다."));

        // DTO → Entity 변환
        RecruitmentPost post = new RecruitmentPost();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        if (postDTO.getDeadline() != null && !postDTO.getDeadline().isEmpty()) {
            post.setDeadline(LocalDate.parse(postDTO.getDeadline(), DateTimeFormatter.ISO_DATE));
        } else {
            throw new IllegalArgumentException("마감일이 올바르지 않습니다.");
        }

        post.setRegion(Region.valueOf(postDTO.getRegion()));
        post.setRecruitedNumber(postDTO.getRecruitedNumber());
        post.setRecruitedField(postDTO.getRecruitedField());
        post.setUser(user);
        post.setProject(project);

        // 진행 방식 관련으로 null 오류가 떠서 만듬
        if (postDTO.getProceedMethod() == null || postDTO.getProceedMethod().isEmpty()) {
            throw new IllegalArgumentException("진행 방식(proceedMethod)이 누락되었습니다.");
        }
        try {
            post.setProceedMethod(ProceedMethod.valueOf(postDTO.getProceedMethod().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 진행 방식입니다. (ONLINE, OFFLINE, REMOTE 중 선택)");
        }

        return postRepository.save(post);
    }

    // ✅ 모집글 수정
    @Override
    @Transactional
    public RecruitmentPost updateRecruitmentPost(Long id, RecruitmentPostDTO postDTO) {
        RecruitmentPost existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 모집글이 없습니다: " + id));

        if (postDTO.getTitle() != null) existingPost.setTitle(postDTO.getTitle());
        if (postDTO.getContent() != null) existingPost.setContent(postDTO.getContent());

        if (postDTO.getDeadline() != null && !postDTO.getDeadline().isEmpty()) {
            existingPost.setDeadline(LocalDate.parse(postDTO.getDeadline(), DateTimeFormatter.ISO_DATE));
        }

        if (postDTO.getRegion() != null) existingPost.setRegion(Region.valueOf(postDTO.getRegion()));

        existingPost.setRecruitedNumber(postDTO.getRecruitedNumber() > 0 ? postDTO.getRecruitedNumber() : 1);
        if (postDTO.getRecruitedField() != null) existingPost.setRecruitedField(postDTO.getRecruitedField());

        if (postDTO.getUserId() != null) {
            User user = userRepository.findById(postDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
            existingPost.setUser(user);
        }

        if (postDTO.getProjectId() != null) {
            Project project = projectRepository.findById(postDTO.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
            existingPost.setProject(project);
        }

        // ✅ 진행 방식 변환 추가 (소문자도 변환 가능하게)
        if (postDTO.getProceedMethod() != null) {
            try {
                existingPost.setProceedMethod(ProceedMethod.valueOf(postDTO.getProceedMethod().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("올바르지 않은 진행 방식입니다. (ONLINE, OFFLINE, REMOTE 중 선택)");
            }
        }

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
