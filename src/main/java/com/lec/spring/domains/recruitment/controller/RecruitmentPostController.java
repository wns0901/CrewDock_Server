package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.service.RecruitmentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitments")
public class RecruitmentPostController {

    private final RecruitmentPostService recruitmentPostService;

    // 모집글 리스트(페이징)
    @GetMapping
    public void recruitmentsPage(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        // TODO: 페이징 처리
    }

    // 모집글 리스트(필터링)
    @GetMapping("/filter")
    public void recruitments(@RequestParam(value = "filterPosts") String filterPosts) {
        // TODO: 필터 처리
    }

    // 모집글 상세 조회
    @GetMapping("/{recruitmentsId}")
    public RecruitmentPost detailRecruitmentPost(@PathVariable("recruitmentsId") Long id) {
        return recruitmentPostService.detailRecruitmentPost(id);
    }

    // 모집글 등록
    @PostMapping
    public void writeRecruitmentPost(@RequestBody RecruitmentPost recruitmentPost) {
        recruitmentPostService.writeRecruitmentPost(recruitmentPost);
    }

    // 모집글 수정
    @PatchMapping("/{recruitmentsId}")
    public void updateRecruitmentPost(@PathVariable("recruitmentsId") Long id, @RequestBody RecruitmentPost recruitmentPost) {
        recruitmentPostService.updateRecruitmentPost(id, recruitmentPost);
    }

    // 모집글 삭제
    @DeleteMapping("/{recruitmentsId}")
    public void deleteRecruitmentPost(@PathVariable("recruitmentsId") Long id) {
        recruitmentPostService.deleteRecruitmentPost(id);
    }
}

