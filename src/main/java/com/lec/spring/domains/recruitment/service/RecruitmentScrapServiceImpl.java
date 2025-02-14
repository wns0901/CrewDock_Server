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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

    //추가
    @Transactional
    @Override
    public ScrappedPostDTO scrapPost(Long postId, Long userId) {
        RecruitmentPost post = recruitmentPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (recruitmentScrapRepository.existsByUserAndRecruitment(user, post)) {
            throw new IllegalStateException("이미 스크랩한 모집글입니다.");
        }

        RecruitmentScrap scrap = RecruitmentScrap.builder()
                .user(user)
                .recruitment(post)
                .build();
        recruitmentScrapRepository.save(scrap);

        // 모집글의 댓글 개수 가져오기.
        long commentCount = commentRepository.countByPostId(postId);

        return new ScrappedPostDTO(
                scrap.getId(),        // RecruitmentScrap ID
                post.getId(),         // 모집글 ID
                post.getTitle(),      // 모집글 제목
                post.getCreatedAt(),  // 모집글 작성 시간
                commentCount,// 댓글 개수
                post.getDeadline()
        );
    }


    // 스크랩 취소
    @Transactional
    public void unScrapPost(Long postId, Long userId) {
        RecruitmentPost post = recruitmentPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 존재 여부 확인 후 삭제
        if (!recruitmentScrapRepository.existsByUserAndRecruitment(user, post)) {
            throw new IllegalStateException("스크랩한 모집글이 아닙니다.");
        }

        recruitmentScrapRepository.deleteByUserAndRecruitment(user, post);
    }


    @Override
    public List<ScrappedPostDTO> getScrappedPosts(Long userId, int row) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return recruitmentScrapRepository.findScrappedPostsWithCommentCount(userId, row);
    }
}