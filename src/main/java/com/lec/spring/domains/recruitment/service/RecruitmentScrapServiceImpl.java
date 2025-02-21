package com.lec.spring.domains.recruitment.service;


import com.lec.spring.domains.recruitment.dto.ScrappedPostDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.recruitment.repository.RecruitmentCommentRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentScrapRepository;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentCommentRepositoryImpl;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecruitmentScrapServiceImpl implements RecruitmentScrapService {
    private final RecruitmentScrapRepository recruitmentScrapRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final UserRepository userRepository;
    private final RecruitmentCommentRepository commentRepository;

    public RecruitmentScrapServiceImpl(RecruitmentScrapRepository recruitmentScrapRepository, RecruitmentPostRepository recruitmentPostRepository, UserRepository userRepository, QRecruitmentCommentRepositoryImpl qrecruitmentCommentRepository, RecruitmentCommentRepository commentRepository) {
        this.recruitmentScrapRepository = recruitmentScrapRepository;
        this.recruitmentPostRepository = recruitmentPostRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }
    @Transactional
    public void addScrap(Long userId, Long recruitmentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(recruitmentId)
                .orElseThrow(() -> new EntityNotFoundException("Recruitment post not found with id: " + recruitmentId));

        // 이미 스크랩한 경우 중복 방지
        Optional<RecruitmentScrap> existingScrap = recruitmentScrapRepository.findByUserAndRecruitment(user, recruitmentPost);
        if (existingScrap.isPresent()) {
            throw new IllegalStateException("Already scrapped this post.");
        }

        RecruitmentScrap scrap = RecruitmentScrap.builder()
                .user(user)
                .recruitment(recruitmentPost)
                .build();
        recruitmentScrapRepository.save(scrap);
    }

    @Transactional
    public void removeScrap(Long userId, Long recruitmentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(recruitmentId)
                .orElseThrow(() -> new EntityNotFoundException("Recruitment post not found with id: " + recruitmentId));

        RecruitmentScrap scrap = recruitmentScrapRepository.findByUserAndRecruitment(user, recruitmentPost)
                .orElseThrow(() -> new EntityNotFoundException("Scrap not found"));

        recruitmentScrapRepository.delete(scrap);
    }


    @Override
    public List<ScrappedPostDTO> getScrappedPosts(Long userId, int row) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return recruitmentScrapRepository.findScrappedPostsWithCommentCount(userId, row);
    }
}