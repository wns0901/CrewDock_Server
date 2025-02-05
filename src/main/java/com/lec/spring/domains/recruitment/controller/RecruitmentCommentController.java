package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import org.springframework.web.bind.annotation.*;

public class RecruitmentCommentController {

    // 해당 모집글에 모든 댓글 불러오기
    @GetMapping("/recruitments/{recruitmentsId}/comments")
    public void commentList(@PathVariable Long recruitmentsId) {

    };

    // 댓글 작성
    @PostMapping("/recruitments/{recruitmentsId}/comments")
    public void createcomment(@RequestBody RecruitmentComment recruitmentComment) {
        // 작성 잘되나
    }

    // 댓글 삭제
    @DeleteMapping ("/recruitments/{recruitmentsId}/comments/{commentId}")
    public void deletecomment(@PathVariable Long recruitmentsId, Long commentId){

    }
}
