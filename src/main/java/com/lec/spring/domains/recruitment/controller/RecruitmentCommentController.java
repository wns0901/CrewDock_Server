package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitments")
@RestController
public class RecruitmentCommentController {

    // 해당 모집글에 모든 댓글 불러오기
    @GetMapping("/{recruitmentsId}/comments")
    public void commentList(@PathVariable Long recruitmentsId) {
        //
    };

    // 댓글 작성
    @PostMapping("/{recruitmentsId}/comments")
    public void createcomment(@RequestBody RecruitmentComment recruitmentComment) {

    }

    // 댓글 삭제
    @DeleteMapping ("/{recruitmentsId}/comments/{commentId}")
    public void deletecomment(@PathVariable Long recruitmentsId, Long commentId){

    }
}
