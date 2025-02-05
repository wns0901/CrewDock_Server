package com.lec.spring.domains.recruitment.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class RecruitmentCommentController {

    ///recruitments/{recruitmentsId}/comments	get			해당 모집글에 모든 댓글 불러오기
    ///recruitments/{recruitmentsId}/comments	post	json body		댓글 작성
    ///recruitments/{recruitmentsId}/comments/{commentId}	delete			댓글 삭제

    @GetMapping("/recruitments/{recruitmentsId}/comments")
    public void commentList(){

    };

    @PostMapping("")
    public void createcomment(){

    }

    @DeleteMapping ("/recruitments/{recruitmentsId}/comments/{commentId}")
    public void deletecomment(){}
}
