package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.service.RecruitmentPostService;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentPostServiceImpl implements RecruitmentPostService {

    private final RecruitmentPostRepository postRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Page<RecruitmentPost> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public List<RecruitmentPost> myRecruitmentPost(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    @Override
    public RecruitmentPost detailRecruitmentPost(Long id) {
        return postRepository.findByIdWithUserAndProject(id)
                .orElseThrow(() -> new EntityNotFoundException("RecruitmentPost not found"));
    }

    @Override
    public RecruitmentPost writeRecruitmentPost(RecruitmentPost post) {
        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Project project = projectRepository.findById(post.getProject().getId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        post.setUser(user);
        post.setProject(project);

        return postRepository.save(post);
    }

    @Override
    public RecruitmentPost updateRecruitmentPost(Long id, RecruitmentPost post) {
        RecruitmentPost existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RecruitmentPost not found"));

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setDeadline(post.getDeadline());
        existingPost.setRegion(post.getRegion());
        existingPost.setProceedMethod(post.getProceedMethod());
        existingPost.setRecruitedNumber(post.getRecruitedNumber());
        existingPost.setRecruitedField(post.getRecruitedField());

        return postRepository.save(existingPost);
    }

    @Override
    public void deleteRecruitmentPost(Long id) {
        RecruitmentPost post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RecruitmentPost not found"));

        postRepository.delete(post);
    }
}
