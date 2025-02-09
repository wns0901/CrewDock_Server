package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentScrapRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecruitmentScrapServiceImpl implements RecruitmentScrapService {
    private final RecruitmentScrapRepository recruitmentScrapRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final UserRepository userRepository;

    public RecruitmentScrapServiceImpl(RecruitmentScrapRepository recruitmentScrapRepository, RecruitmentPostRepository recruitmentPostRepository, UserRepository userRepository) {
        this.recruitmentScrapRepository = recruitmentScrapRepository;
        this.recruitmentPostRepository = recruitmentPostRepository;
        this.userRepository = userRepository;
    }

    // 모집글 스크랩
    @Transactional
    public void scrapPost(Long postId, Long userId) {
        RecruitmentPost post = recruitmentPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (recruitmentScrapRepository.existsByUserIdAndRecruitment(user, post)) {
            throw new IllegalStateException("이미 스크랩한 모집글입니다.");
        }

        RecruitmentScrap scrap = RecruitmentScrap.builder().
                userId(user).
                recruitment(post).
                build();
        recruitmentScrapRepository.save(scrap);
    }

    // 스크랩 취소
    @Transactional
    public void unScrapPost(Long postId, Long userId) {
        RecruitmentPost post = recruitmentPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        recruitmentScrapRepository.deleteByUserIdAndRecruitment(user, post);
    }

    // 내가 스크랩한 모집글 조회
    @Override
    public List<RecruitmentScrap> getScrappedPosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return recruitmentScrapRepository.findScrapsByUserId(user);
    }

    @Override
    public List<RecruitmentScrap> getScrappedPostsWithLimit(Long userId, int row) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return recruitmentScrapRepository.findScrapsByUserId(user, row);
    }
}