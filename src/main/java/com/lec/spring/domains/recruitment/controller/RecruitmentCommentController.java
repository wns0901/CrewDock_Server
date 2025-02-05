package com.lec.spring.domains.recruitment.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class RecruitmentCommentController {

    // 해당 모집글에 모든 댓글 불러오기
    @GetMapping("/recruitments/{recruitmentsId}/comments")
    public void commentList(@PathVariable Long recruitmentsId) {

    };

    // 댓글 작성
    @PostMapping("/recruitments/{recruitmentsId}/comments")
    public void createcomment(@PathVariable Long recruitmentsId){

    }

    // 댓글 삭제
    @DeleteMapping ("/recruitments/{recruitmentsId}/comments/{commentId}")
    public void deletecomment(@PathVariable Long recruitmentsId, @PathVariable Long commentId){}
}
