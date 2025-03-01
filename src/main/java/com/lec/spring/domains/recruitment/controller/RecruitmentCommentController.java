package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.dto.AllRecruitmentCommentDTO;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentCommentDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.service.RecruitmentCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitments/{recruitmentsId}/comments")
@RequiredArgsConstructor
public class RecruitmentCommentController {

    private final RecruitmentCommentService commentService;

    // 해당 모집글의 모든 댓글 + 대댓글 조회
    @GetMapping
    public ResponseEntity<AllRecruitmentCommentDTO> commentList(@PathVariable Long recruitmentsId) {
        return ResponseEntity.ok(commentService.findCommentList(recruitmentsId));
    }

    // 댓글 작성 (부모 댓글 ID가 있으면 대댓글 등록)
    @PostMapping
    public ResponseEntity<RecruitmentCommentDTO> createComment(
            @PathVariable Long recruitmentsId,
            @RequestBody RecruitmentCommentDTO commentDTO) {

        // userId가 null이면 예외 처리
        if (commentDTO.getUserId() == null) {
            throw new IllegalArgumentException("유저 ID가 필요합니다.");
        }

        RecruitmentCommentDTO newComment = commentService.createRecruitmentComment(
                recruitmentsId,
                commentDTO.getUserId(), // userId 사용
                commentDTO.getContent(),
                commentDTO.getParentCommentId() // 부모 댓글 ID 사용
        );

        return ResponseEntity.ok(newComment);
    }


    //  모집글 내 댓글 개수 조회
    @GetMapping("/count")
    public ResponseEntity<Integer> countComments(@PathVariable Long recruitmentsId) {
        int count = commentService.countRecruitmentComment(recruitmentsId);
        return ResponseEntity.ok(count);
    }

    // 댓글 삭제 (대댓글이 있으면 "삭제된 댓글입니다." 처리, 없으면 삭제)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @PathVariable String recruitmentsId) {
        String result = commentService.deleteRecruitmentComment(commentId);
        return ResponseEntity.ok(result);
    }
}

