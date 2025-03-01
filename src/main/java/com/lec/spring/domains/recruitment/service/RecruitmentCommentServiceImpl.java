package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.post.dto.AllPostCommentDTO;
import com.lec.spring.domains.recruitment.dto.AllRecruitmentCommentDTO;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentCommentDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentCommentRepository;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentCommentRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentCommentServiceImpl implements RecruitmentCommentService {

    private final RecruitmentCommentRepository recruitmentCommentRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final QRecruitmentCommentRepository qRecruitmentCommentRepository;
    private final UserRepository userRepository;

    @Override
    public AllRecruitmentCommentDTO findCommentList(Long postId) {
        List<RecruitmentComment> comments = recruitmentCommentRepository.findByPostId(postId);
        Integer count = comments.size();
        return AllRecruitmentCommentDTO.builder()
                .count(count)
                .comments(comments.stream().map(RecruitmentCommentDTO::fromEntity).toList())
                .build();
    }

    @Override
    public RecruitmentCommentDTO createRecruitmentComment(Long postId, Long userId, String content, Long parentCommentId) {
        RecruitmentPost post = recruitmentPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        RecruitmentComment parentComment = null;
        if (parentCommentId != null) {
            System.out.println(" 저장하려는 부모 댓글 ID: " + parentCommentId);
            parentComment = recruitmentCommentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException(" 부모 댓글을 찾을 수 없습니다!")); // 부모 댓글 체크
        }

        RecruitmentComment newComment = RecruitmentComment.builder()
                .post(post)
                .user(user)
                .content(content)
                .comment(parentComment) // 부모 댓글 매핑
                .deleted(false)
                .build();

        RecruitmentComment savedComment = recruitmentCommentRepository.save(newComment);

        System.out.println("저장된 댓글: " + savedComment.getId() + ", 부모 댓글 ID: " +
                (savedComment.getComment() != null ? savedComment.getComment().getId() : "없음"));

        return RecruitmentCommentDTO.fromEntity(savedComment);
    }



    // 모집글 내 전체 댓글 개수 조회
    @Override
    public int countRecruitmentComment(Long postId) {
        RecruitmentPost post = recruitmentPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));
        return recruitmentCommentRepository.countByPostId(post).intValue();
    }

    // 댓글 삭제 (대댓글 존재 시 "삭제된 댓글입니다." 처리)
    @Override
    public String deleteRecruitmentComment(Long commentId) {
        RecruitmentComment comment = recruitmentCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        List<RecruitmentComment> replies = qRecruitmentCommentRepository.findRepliesByParentComment(comment);

        if (!replies.isEmpty()) {
            comment.setDeleted(true);
            recruitmentCommentRepository.save(comment);
            return "parent";
        } else {
            recruitmentCommentRepository.delete(comment);
            return "deleted";
        }
    }
}
