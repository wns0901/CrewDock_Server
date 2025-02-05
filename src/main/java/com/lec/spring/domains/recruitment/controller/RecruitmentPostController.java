package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.service.RecruitmentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RecruitmentPostController {

    private RecruitmentPostService recruitmentPostService;

    // 메인페이지에 띄울 모집글 리스트(페이징)
    @GetMapping("/recruitments?[page]")
    public void recruitmentsPage(@RequestParam(value = "page", defaultValue = "1") Integer page){

    }

    // 메인페이지에 띄울 모집글 리스트(필터)
    @GetMapping("/recruitments?[...]")
    public void recruitments(@RequestParam(value = "filterPosts") String filterPosts){

    }

    // 모집글 상세 글(댓글, 첨부파일 포함)
    @GetMapping("/recruitments/{recruitmentsId}")
    public void detailRecruitmentPost(@PathVariable Long id, RecruitmentPost recruitmentPost) {

    }

    // 모집글 등록
    @PostMapping("/recruitments")
    public void writeRecruitmentPost(RecruitmentPost recruitmentPost) {

    }

    // 모집글 수정
    @PatchMapping("/recruitments")
    public void updateRecruitmentPost(RecruitmentPost recruitmentPost) {

    }

    // 모집글 삭제
    @DeleteMapping("/recruitments/{recruitmentsId}")
    public void deleteRecruitmentPost(RecruitmentPost recruitmentPost) {

    }
}
